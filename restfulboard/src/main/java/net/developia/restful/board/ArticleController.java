package net.developia.restful.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("board/{boa_no}/{pg}")
public class ArticleController {
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private BoardService boardService;

//	@GetMapping("/")
//	public void list() {}
	
//	@GetMapping("insert")
//	public void insert() {}
	
	@GetMapping("insert")
	public String insert() {
		return "board/insert";
	}
	

	
	@GetMapping("/")
	public String list(
		@PathVariable int boa_no , Model model) {
		
		try {
			BoardDTO boardDTO = boardService.getBoard(boa_no);
			
			List<ArticleDTO> list= boardService.getArticleList(boa_no);
			model.addAttribute("boardDTO", boardDTO);
			model.addAttribute("list", list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "board/list";
	}
	
	@PostMapping("insert")
	public ModelAndView insert(
		@ModelAttribute ArticleDTO articleDTO, 
		HttpSession session,
		HttpServletRequest request) {
		
		articleDTO.setArt_ip(request.getRemoteAddr());
		articleDTO.setUserDTO((UserDTO)session.getAttribute("userInfo"));
		logger.info(articleDTO.toString());
		try {
			boardService.insertArticle(articleDTO);
			return new ModelAndView("redirect:./");
		} catch (Exception e) {
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("result");
			mav.addObject("msg","�Է� ����");
			mav.addObject("url","javascript:history.back();");
			return mav;
		}
	}

}
