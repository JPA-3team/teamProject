package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

	private final MenuRepository menuRepository;
	private final ModelMapper modelMapper;

	// @Autowired를 작성하지 않아도 자동 적용됨을 잊지 말자.
	public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
		this.menuRepository = menuRepository;
		this.modelMapper = modelMapper;
	}
	
	/* 목차. 1. findById */
	public MenuDTO findMenuByCode(int menuCode) {
		
		/* 설명. findById 메소드는 이미 구현되어 있으므로 인터페이스에 따로 정의할 필요가 없다.
		 *  findById 의 반환값은 Optional 타입이다. Optional 타입은 NPE을 방지하기 위한 다양한 기능이 존재한다.
		 *  해당 id로 조회되지 못했을 경우 IllegalArgumentException을 발생시킨다. */
return null;
	}
	
	/* 목차. 2. findAll -> 페이징 처리 전 */
	public List<MenuDTO> findMenuList() {
		
		/* 설명. findAll 메소드는 이미 구현 되어 있으므로 인터페이스에 따로 정의할 필요가 없다.
		 *  Sort(정렬) 기준을 전달하며 조회할 수도 있다.
		 * */
		List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());

		return menuList
				.stream()
				.map(menu -> modelMapper.map(menu, MenuDTO.class))
				.collect(Collectors.toList());

	}

	
	/* 목차. 4. QueryMethod */
	/* 설명. MenuRepository에 세 종류의 메서드가 정의되어 있고, 아래 목차 1~3 메서드를 주석처리 하며 테스트한다. */
    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {

        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice);

        return menuList
                .stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());

    }

	/* 목차. 5. JPQL or native query */
	// CategoryService 참고

	/* 목차. 6. save - 엔티티 저장 */
	@Transactional
	public void registNewMenu(MenuDTO newMenu) {
		
		/* 설명. CrudRepository에 미리 정의 되어 있는 save() 메소드를 통해 하나의 엔티티를 저장할 수 있다. */

		menuRepository.save(modelMapper.map(newMenu, Menu.class));
	}

	/* 목차. 7. 수정하기 - 엔티티 조회 후 값 변경 */
	@Transactional
	public void modifyMenu(MenuDTO modifyMenu) {


	}

	/* 목차. 8. delete */
	@Transactional
	public void deleteMenu(Integer menuCode) {


	}



}
