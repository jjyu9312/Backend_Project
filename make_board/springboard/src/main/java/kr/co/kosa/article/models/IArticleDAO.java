package kr.co.kosa.article.models;

import java.util.List;

public interface IArticleDAO {

	void insertArticle(ArticleDTO articleDTO) throws Exception;

	List<ArticleDTO> getArticleList(PageDTO pageDTO) throws Exception;

	ArticleDTO getArticle(long no) throws Exception;

	void updateArticle(ArticleDTO articleDTO) throws Exception;

	void deleteArticle(ArticleDTO articleDTO) throws Exception;


	long getTotalArticleCount() throws Exception;

	void updateReadCount(long no) throws Exception;
	


}
