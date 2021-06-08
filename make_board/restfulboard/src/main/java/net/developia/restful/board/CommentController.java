package net.developia.restful.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.developia.restful.user.UserDTO;

@Controller
@RequestMapping("board/{boa_no}/{pg}/{art_no}/{cpg}")
public class CommentController {

	private static Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/")
	@ResponseBody
	public List<CommentDTO> comment_list(@ModelAttribute CommentDTO commentDTO) throws Exception {
		return boardService.getCommentList(commentDTO);
	}
	
	@PostMapping("/")
	@ResponseBody
	public List<CommentDTO> comment_insert(
		@ModelAttribute CommentDTO commentDTO, 
		HttpServletRequest request,
		HttpSession session) {
		
		commentDTO.setCom_ip(request.getRemoteAddr());
		commentDTO.setUserDTO((UserDTO) session.getAttribute("userInfo"));
		
		logger.info("comment_insert() 메소드 수행");
		logger.info(commentDTO.toString());
		try {
			boardService.insertComment(commentDTO);
			return boardService.getCommentList(commentDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@PostMapping("/{com_no}")
	@ResponseBody
	public List<CommentDTO> comment_update (
			@ModelAttribute CommentDTO commentDTO, 
			HttpServletRequest request,
			HttpSession session) throws Exception {
		commentDTO.setCom_ip(request.getRemoteAddr());
		commentDTO.setUserDTO((UserDTO) session.getAttribute("userInfo"));
		
		logger.info("comment_update() 메소드 수행");
		logger.info(commentDTO.toString());

		boardService.updateComment(commentDTO);
		return boardService.getCommentList(commentDTO);
	}
	
	@DeleteMapping("/{com_no}")
	@ResponseBody
	public List<CommentDTO> comment_delete(
			@ModelAttribute CommentDTO commentDTO, 
			HttpServletRequest request,
			HttpSession session) throws Exception {
		commentDTO.setCom_ip(request.getRemoteAddr());
		commentDTO.setUserDTO((UserDTO) session.getAttribute("userInfo"));
		
		logger.info("comment_delete() 메소드 수행");
		logger.info(commentDTO.toString());
		
		boardService.deleteComment(commentDTO);
		return boardService.getCommentList(commentDTO);
	}
}