package net.developia.restful.board;



import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.developia.restful.user.UserController;
import net.developia.restful.user.UserDTO;

@Controller
@RequestMapping("board/{boa_no}/{pg}/{art_no}")
public class ArticleDetailController {
	private static final Logger logger =
		LoggerFactory.getLogger(ArticleDetailController.class);

	@Autowired
	private BoardService boardService;

//	@GetMapping("/")
//	public void detail() {}
		
	@GetMapping("/update")
	public String update(@PathVariable long art_no, Model model) {
		try {
			boardService.updateReadcnt(art_no);
			ArticleDTO articleDTO = boardService.getArticle(art_no);
			model.addAttribute("articleDTO", articleDTO);
			return "board/update";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "../");
			return "result";
		}
	}
	
	@PostMapping("/update")
	public ModelAndView update(@ModelAttribute ArticleDTO articleDTO, HttpSession session) {
		articleDTO.setUserDTO((UserDTO)session.getAttribute("userInfo"));
		try {
			boardService.updateArticle(articleDTO);
			ModelAndView mav = new ModelAndView("result");
			mav.addObject("msg", articleDTO.getArt_no() + "�� �Խù��� �����Ǿ����ϴ�.");
			mav.addObject("url", "./");
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("result");
			mav.addObject("msg", e.getMessage());
			mav.addObject("url", "javascript:history.back();");
			return mav;
		}
	}

	
	@GetMapping("/delete")
	public ModelAndView delete(@ModelAttribute ArticleDTO articleDTO, HttpSession session) {
		articleDTO.setUserDTO((UserDTO)session.getAttribute("userInfo"));
		
		try {
			boardService.deleteArticle(articleDTO);
			ModelAndView mav = new ModelAndView("result");
			mav.addObject("msg", articleDTO.getArt_no() + "�� �Խù��� �����Ǿ����ϴ�.");
			mav.addObject("url", "../");
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("result");
			mav.addObject("msg", e.getMessage());
			mav.addObject("url", "javascript:history.back();");
			return mav;
		}
	}
	
	
	@GetMapping("/")
	public String detail(
		@PathVariable int boa_no, 
		@PathVariable long pg, 
		@PathVariable long art_no,
		Model model) {
		
		logger.info("�Խ��� ��ȣ : " + boa_no);
		logger.info("������ ��ȣ : " + pg);
		logger.info("�Խù� ��ȣ : " + art_no);
		
		try {
			boardService.updateReadcnt(art_no);
			ArticleDTO articleDTO = boardService.getArticle(art_no);
			model.addAttribute("articleDTO", articleDTO);
			return "board/detail";
		} catch (Exception e) {
			e.printStackTrace();

			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "../");
			return "result";
		}
	}
}
