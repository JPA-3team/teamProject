package com.ohgiraffers.springdatajpa.menu.menu.service;

import com.ohgiraffers.springdatajpa.menu.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

	private final MenuRepository menuRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
		this.menuRepository = menuRepository;
		this.modelMapper = modelMapper;
	}

	public MenuDTO findMenuByCode(int menuCode) {

		Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new); // 람다식

		return modelMapper.map(menu, MenuDTO.class);
	}
	
	/* findAll -> 페이징 처리 전 */
	public List<MenuDTO> findMenuList() {

		List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());

		return menuList
				.stream()
				.map(menu -> modelMapper.map(menu, MenuDTO.class))
				.collect(Collectors.toList());

	}

	/* Page -> 페이징 처리 후 */
	public Page<MenuDTO> findMenuList(Pageable pageable) {

		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
				pageable.getPageSize(),
				Sort.by("menuCode").descending());

		Page<Menu> menuList = menuRepository.findAll(pageable);

		return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
	}

    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {

        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice);

        return menuList
                .stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());

    }

	// CategoryService 참고

	@Transactional
	public void registNewMenu(MenuDTO newMenu) {

		menuRepository.save(modelMapper.map(newMenu, Menu.class));
	}

	@Transactional
	public void modifyMenu(MenuDTO modifyMenu) {

		Menu foundMenu = menuRepository.findById(modifyMenu.getMenuCode()).orElseThrow(IllegalArgumentException::new);
		foundMenu.setMenuName(modifyMenu.getMenuName());
	}

	@Transactional
	public void deleteMenu(Integer menuCode) {

		menuRepository.deleteById(menuCode);

	}
	
	
}
