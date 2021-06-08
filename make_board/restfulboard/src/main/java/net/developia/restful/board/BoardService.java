package net.developia.restful.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public interface BoardService {

//	@Autowired
//	private BoardDAO boardDAO;
	
	List<BoardDTO> getBoardList() throws Exception;
	
	
	void insertBoard(BoardDTO boardDTO) throws Exception;
	
	
	void insertArticle(ArticleDTO articleDTO) throws Exception;

	BoardDTO getBoard(int boa_no) throws Exception;

	List<ArticleDTO> getArticleList(int boa_no) throws Exception;


	ArticleDTO getArticle(long art_no) throws Exception;


	void updateReadcnt(long art_no) throws Exception;


	void deleteArticle(ArticleDTO articleDTO) throws Exception;


	void updateArticle(ArticleDTO articleDTO) throws Exception;


	void updateComment(CommentDTO commentDTO) throws Exception;


	List<CommentDTO> getCommentList(CommentDTO commentDTO) throws Exception;


	void deleteComment(CommentDTO commentDTO) throws Exception;


	void insertComment(CommentDTO commentDTO) throws Exception;

}
