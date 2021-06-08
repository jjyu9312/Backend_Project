package net.developia.restful.board;

import java.sql.SQLException;
import java.util.List;

public interface BoardDAO {

	List<BoardDTO> getBoardList();

	void insertBoard(BoardDTO boardDTO) throws Exception;

	void insertArticle(ArticleDTO articleDTO) throws Exception;

	BoardDTO getBoard(int boa_no) throws Exception;

	List<ArticleDTO> getArticleList(int boa_no) throws Exception;

	ArticleDTO getArticle(long art_no) throws Exception;

	int updateReadcnt(long art_no) throws Exception;

	int deleteArticle(ArticleDTO articleDTO) throws Exception;
	
	int updateArticle(ArticleDTO articleDTO) throws SQLException;

	void insertComment(CommentDTO commentDTO) throws Exception;

	List<CommentDTO> getCommentList(CommentDTO commentDTO) throws Exception;

	void deleteComment(CommentDTO commentDTO) throws Exception;

	int updateComment(CommentDTO commentDTO) throws Exception;
}
