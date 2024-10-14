package com.ohgiraffers.springdatajpa.menu.menu.controller;

import java.util.List;

import com.ohgiraffers.springdatajpa.menu.common.PagingButtonInfo;
import com.ohgiraffers.springdatajpa.menu.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {

	private final MenuService menuService;

	@Autowired
	public MenuController(MenuService menuService) {

		this.menuService = menuService;
	}
	
	@GetMapping("/{menuCode}")
	public String findMenuByCode(@PathVariable int menuCode, Model model) {


		MenuDTO menu = menuService.findMenuByCode(menuCode);

		model.addAttribute("menu", menu);

		return "menu/detail";

	}
	
	/* 설명. JPA 페이징 처리 미적용 */
//	@GetMapping("/list")
//	public String findMenuList(Model model) {
//
//		List<MenuDTO> menuList = menuService.findMenuList();
//
//		model.addAttribute("menuList", menuList);
//
//		return "menu/list";
//	}
	
	/* 설명. JPA 페이징 처리 적용 */
	/**
	 * 주어진 Pageable 정보를 바탕으로 메뉴 리스트를 조회하고, Model에 페이지네이션 정보를 추가한 후 반환한다.
	 *
	 * <p>{@link org.springframework.data.domain.Pageable} 객체를 인자로 받아 페이지 요청 정보를
	 * 처리한다. @PageableDefault 어노테이션을 통해 기본 페이지 설정을 지정할 수 있다.</p>
	 * <p>예를 들어, 기본 페이지 크기나 정렬 기준을 설정할 수 있으며, 필요한 경우 클라이언트 요청에 따라 동적으로
	 * 변경될 수도 있다.</p>
	 *
	 * @param pageable {@link org.springframework.data.domain.Pageable} 객체로, 페이지 번호, 크기, 정렬 정보를 관리한다.
	 *                  {@code @PageableDefault(size = 10, sort = "name")} 와 같이 기본 페이지 크기를 10, 정렬 기준을 이름으로 설정할 수 있다.
	 * @param model {@link org.springframework.ui.Model} 객체로, 뷰에 페이지 정보와 메뉴 리스트를 추가하는 데 사용된다.
	 * @return 조회된 {@link java.util.List} 객체로, DB로부터 검색된 메뉴 리스트를 반환한다.
	 */
	@GetMapping("/list")
	public String findMenuList(@PageableDefault Pageable pageable, Model model) {


		/* page -> number, size, sort 파라미터가 Pageable 객체에 담긴다. */
		log.info("pageable : {}", pageable);

		Page<MenuDTO> menuList = menuService.findMenuList(pageable);

		log.info("조회한 내용 목록 : {}", menuList.getContent());
		log.info("총 페이지 수 : {}", menuList.getTotalPages());
		log.info("총 메뉴 수 : {}", menuList.getTotalElements());
		log.info("해당 페이지에 표시 될 요소 수 : {}", menuList.getSize());
		log.info("해당 페이지에 실제 요소 수 : {}", menuList.getNumberOfElements());
		log.info("첫 페이지 여부 : {}", menuList.isFirst());
		log.info("마지막 페이지 여부 : {}", menuList.isLast());
		log.info("정렬 방식 : {}", menuList.getSort());
		log.info("여러 페이지 중 현재 인덱스 : {}", menuList.getNumber());

		PagingButtonInfo paging = com.ohgiraffers.springdatajpa.common.Pagenation.getPagingButtonInfo(menuList);

		model.addAttribute("paging", paging);
		model.addAttribute("menuList", menuList);

		return "menu/list";
	}

	/*쿼리메소드 버튼을 누르면 이 페이지를 보여준다.*/
	@GetMapping("/querymethod")
	public void queryMethodPage() { }

	/*쿼리메소드 페이지의 버튼 findMenuByMenuPriceGreatThen(금액) */
	@GetMapping("/search")
	public String findByMenuPrice(@RequestParam Integer menuPrice, Model model) {

		List<MenuDTO> menuList = menuService.findByMenuPrice(menuPrice);

		model.addAttribute("menuList", menuList);
		model.addAttribute("menuPrice", menuPrice);

		return "menu/searchResult";

	}

	@GetMapping("/regist")
	public void registPage() {}

	@PostMapping("/regist")
	public String registNewMenu(MenuDTO newMenu) {

		menuService.registNewMenu(newMenu);

		return "redirect:/menu/list";

	}
	
	@GetMapping("/modify")
	public void modifyPage() {}

	@PostMapping("/modify")
	public String modifyMenu(MenuDTO modifyMenu) {

		menuService.modifyMenu(modifyMenu);

		return "redirect:/menu/" + modifyMenu.getMenuCode();
	}

	/*get 매핑으로 메인에서 메뉴삭제하기 버튼 눌러서 가는 페이지 */
	@GetMapping("/delete")
	public void deletePage() {}

	/*메뉴삭제 페이지에서 인풋 post 폼 메뉴번호로 삭제 */
	@PostMapping("/delete")
	public String deleteMenu(@RequestParam Integer menuCode) {

		menuService.deleteMenu(menuCode);

		return "redirect:/menu/list";

	}

}
