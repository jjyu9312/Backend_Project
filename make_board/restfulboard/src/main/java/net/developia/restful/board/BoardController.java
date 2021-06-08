package net.developia.restful.board;

import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import net.developia.restful.user.UserController;
import net.developia.restful.user.UserDTO;

@Controller
@RequestMapping("board")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private BoardService boardService;
	
//	@GetMapping("/")	
//	public String list() {
//		return "board/board_list";
//	}
	
	@GetMapping("/")
	public String list(Model model) {
		try {
			List<BoardDTO> list = boardService.getBoardList();
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "board/board_list";
	}

	
	@GetMapping("insert")
	public String insert() {
		return "board/board_insert";
	}
		
	@PostMapping("insert")
	public ModelAndView insert(@ModelAttribute BoardDTO boardDTO, HttpSession session) {
		boardDTO.setUserDTO((UserDTO)session.getAttribute("userInfo"));
		try {
			boardService.insertBoard(boardDTO);
			ModelAndView mav = new ModelAndView("result");
			mav.addObject("msg", boardDTO.getBoa_name() + "이 생성되었습니다.");
			mav.addObject("url", "./");
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("result");
			mav.addObject("msg", boardDTO.getBoa_name() + "생성 실패");
			mav.addObject("url", "./");
			return mav;
		}
	}
}
