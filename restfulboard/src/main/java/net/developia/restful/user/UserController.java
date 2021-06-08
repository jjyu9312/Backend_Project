package net.developia.restful.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.developia.restful.board.ArticleDTO;
import net.developia.restful.board.BoardService;

@Controller
@RequestMapping("user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String login() {
		return "user/login";
	}
//	
//	@PostMapping("/")
//	public ModelAndView login(
//			@ModelAttribute UserDTO userDTO) {
//		logger.info(userDTO.toString());
//		
//		
//		return null;
//	}
	@PostMapping("/")
	public String login(
			@ModelAttribute UserDTO userDTO,
			Model model,
			HttpSession session) {
		logger.info(userDTO.toString());
		try {
			UserDTO userInfo = userService.getUser(userDTO);
			logger.info(userInfo.toString());
			
			session.setAttribute("userInfo", userInfo);
			return "redirect:../board/";
		} catch (Exception e) {
			logger.info(e.getMessage());
			model.addAttribute("msg",e.getMessage());
			model.addAttribute("url", "./");
			return "result";
		}
	}
	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		UserDTO userInfo = (UserDTO) session.getAttribute("userInfo");
		session.invalidate();
		
		ModelAndView mav = new ModelAndView("result");
		mav.addObject("msg", userInfo.getUsr_name() + 
				 "(" + userInfo.getUsr_id() + ")님이 로그아웃 하였습니다.");
		mav.addObject("url", "./");
		return mav;
	}
	
}