package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;

@Controller
@RequestMapping(value="/guestbook")
public class GuestController{
	
	//필드
	@Autowired
	private GuestDao guestDao;

	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model){   
		System.out.println("list");
		
		List<GuestVo> guestList = guestDao.ListAllGuest(); 
		System.out.println(guestList.toString());
		
		//model에 담아 보내기
		model.addAttribute("gList", guestList);
		
		/* 포워드 */		
		return "guestList";
	}
	
	// @ModelAttribute
	@RequestMapping(value="/insert", method= {RequestMethod.GET, RequestMethod.POST})
	public String insert(@ModelAttribute GuestVo guestVo){
		System.out.println("insert");

		System.out.println(guestVo.toString());
		
		/* insert 저장 */
		guestDao.guestInsert(guestVo);
		
		/* 리다이렉트 */		
		return "redirect:/guestbook/list";
	}
	
		
	@RequestMapping(value="/deleteForm",method= {RequestMethod.GET, RequestMethod.POST})
	public String deleteForm() {
		System.out.println("deleteForm");
	
		/* 포워드 */
		return "deleteForm";
	}
		
	// @ModelAttribute
	@RequestMapping(value="/delete",method= {RequestMethod.GET, RequestMethod.POST})
	public String delete(@ModelAttribute GuestVo guestVo, Model model) {
		System.out.println("delete");
		
		System.out.println(guestVo.toString());
		
		int result = guestDao.guestDelete(guestVo);
		
		if(result == 1) {
			//성공 : redirect
			return "redirect:/guestbook/list";
		} else {
			model.addAttribute("result", result);
			//실패  : redirect - guestbook/deleteForm?no=88&result=0
			return "redirect:/guestbook/deleteForm?no="+guestVo.getNo();
		}
		
		/*
		 tip 1! 실패  - 포워드는 redirect로 바꾸기, 파라미터 no가 있어야 한다. 기존 deleteForm의 형식을 체크한다.
		 tip 2! result보다는 count같은 명확한 것이 좋을 것이다.
 		              현재  result값으로 deleteForm에서 문구를 보내고 있다. //<c:if test="${requestScope.result == 0}">
		if(result == 1) {
			//성공
			return "redirect:/guestbook/list";
		} else {
			
			model.addAttribute("result", result);
			//실패
			return "deleteForm";
		} */
	}
		
}
