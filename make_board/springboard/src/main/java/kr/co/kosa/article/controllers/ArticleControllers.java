package kr.co.kosa.article.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.kosa.article.models.ArticleDTO;
import kr.co.kosa.article.models.IArticleDAO;
import kr.co.kosa.article.models.PageDTO;


@Controller
@RequestMapping("article")
public class ArticleControllers {
	
	@Autowired
	private IArticleDAO articleDAO;
	
	private static Logger logger = LoggerFactory.getLogger(ArticleControllers.class);

	@RequestMapping("list")
	public ModelAndView list(@RequestParam(defaultValue = "1") long pg) {
		long pageSize = 10;
		long blockSize = 10;
		long startNum = (pg-1) * pageSize + 1;
		long endNum = pg * pageSize;
		long startPage = (pg-1) / blockSize * blockSize + 1;
		long endPage = (pg-1) / blockSize * blockSize + blockSize;
		
		try {
			long totalRecordCount = articleDAO.getTotalArticleCount();
			long totalPageCount = totalRecordCount/pageSize;
			if(totalRecordCount%pageSize > 0) totalPageCount++;
			if(endPage > totalPageCount) endPage = totalPageCount;
			PageDTO pageDTO = new PageDTO(startNum, endNum);
			
			List<ArticleDTO> list = articleDAO.getArticleList(pageDTO);
			return new ModelAndView("article/list","list", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("result");
			mav.addObject("msg", "????? ??? ????");
			mav.addObject("url", "../");
			return mav;
		}
		
	}
	@RequestMapping(value="insert", method=RequestMethod.GET)
	public ModelAndView Insert() {
		return new org.springframework.web.servlet.ModelAndView("article/insert");
	}
	@PostMapping(value="insert")
	public ModelAndView Insert(@ModelAttribute ArticleDTO articleDTO) {
		
		logger.info(articleDTO.toString());
		ModelAndView mav = new ModelAndView();
		
		
		try {
			articleDAO.insertArticle(articleDTO);
			mav.setViewName("redirect:list");
			mav.addObject("msg", "?? ??? ????");
			mav.addObject("url", "list");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("msg", "?? ??? ????");
			mav.addObject("url", "javascript:history.back();");
		}
		return mav;
		
	}
	@GetMapping(value="update")
	public ModelAndView Update(@RequestParam long no) throws Exception {
		
		ArticleDTO articleDTO = articleDAO.getArticle(no);
		return new ModelAndView("article/update", "articleDTO", articleDTO);	
	}
	
	@PostMapping(value="update")
	public ModelAndView Update(@ModelAttribute ArticleDTO articleDTO) {
		logger.info(articleDTO.toString());
		ModelAndView mav = new ModelAndView();
		
		try {
			articleDAO.updateArticle(articleDTO);
			mav.addObject("msg", "?? ???? ????");
			mav.addObject("url", "view?no=" + articleDTO.getNo());
		} catch (RuntimeException e) {
			e.printStackTrace();
			mav.addObject("msg", e.getMessage());
			mav.addObject("url", "javascript:history.back();");
		} catch (Exception e) {
			mav.addObject("msg","?????? ??? ???? ????? ???? ????");
			mav.addObject("url", "javascript:history.back();");
		}
		return mav;
	}
	
	@GetMapping("delete")
	public ModelAndView delete(@RequestParam long no ) throws Exception {
		
		ArticleDTO articleDTO = articleDAO.getArticle(no);
		return new ModelAndView("article/delete", "articleDTO", articleDTO);
		
	}
	
	@PostMapping("delete")
	public ModelAndView delete(@ModelAttribute ArticleDTO articleDTO) throws Exception {
		
		
		logger.info(articleDTO.toString());
		
		
		ModelAndView mav = new ModelAndView("result");

		try {
			articleDAO.deleteArticle(articleDTO);
			mav.addObject("msg", "?? ???? ????");
			mav.addObject("url", "list");
		} catch (RuntimeException e) {
			e.printStackTrace();
			mav.addObject("msg", e.getMessage());
			mav.addObject("url", "javascript:history.back();");
		} catch (Exception e) {
			mav.addObject("msg","?????? ??? ???? ????? ???? ????");
			mav.addObject("url", "javascript:history.back();");
		
		}
		return mav;
	}
	
	@RequestMapping("view")
	public ModelAndView view(@RequestParam (defaultValue = "0") long no) throws Exception {
		try
		{
			articleDAO.updateReadCount(no);
			ArticleDTO articleDTO = articleDAO.getArticle(no);
			ModelAndView mav = new ModelAndView();
			mav.setViewName("article/content");
			mav.addObject("articleDTO", articleDTO);
			return mav;
		}catch (RuntimeException e){
			ModelAndView mav = new ModelAndView("result");
			mav.addObject("msg", e.getMessage());
			mav.addObject("url", "list");
			return mav;
		}
	}
}
