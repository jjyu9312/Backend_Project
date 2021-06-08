package net.developia.restful.board;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDAO boardDAO;
	

	public BoardDTO getBoard(int boa_no) throws Exception {
		BoardDTO boardDTO = boardDAO.getBoard(boa_no);
		if (boardDTO == null) {
			throw new RuntimeException("�߸��� �Խ��� ��ȣ�Դϴ�.");
		}
		return boardDTO;
	}


	public List<ArticleDTO> getArticleList(int boa_no) throws Exception {
		return boardDAO.getArticleList(boa_no);
	}
	public void insertBoard(BoardDTO boardDTO) throws Exception {
		boardDAO.insertBoard(boardDTO);
	}
	public void insertArticle(ArticleDTO articleDTO) throws Exception {
		boardDAO.insertArticle(articleDTO);
	}


	@Override
	public List<BoardDTO> getBoardList() throws Exception {
		return boardDAO.getBoardList();
	}


	@Override
	public ArticleDTO getArticle(long art_no) throws Exception {
		// TODO Auto-generated method stub
		ArticleDTO articleDTO = boardDAO.getArticle(art_no);
		if (articleDTO == null) {
			throw new RuntimeException(art_no +"�� ���� �������� �ʽ��ϴ�.");
		}
		return articleDTO;
	}


	@Override
	public void updateReadcnt(long art_no) throws Exception {
		// TODO Auto-generated method stub
		if (boardDAO.updateReadcnt(art_no) == 0) {
			throw new RuntimeException(art_no +"�� ���� �������� �ʽ��ϴ�.");
		}
	}


	@Override
	public void deleteArticle(ArticleDTO articleDTO) throws Exception {
		// TODO Auto-generated method stub
		if (boardDAO.deleteArticle(articleDTO) == 0) {
			throw new RuntimeException("�������� �ʰų� ���� ������ �����ϴ�.");
		}
	}


	@Override
	public void updateArticle(ArticleDTO articleDTO) throws Exception {
		// TODO Auto-generated method stub
		if (boardDAO.deleteArticle(articleDTO) == 0) {
			throw new RuntimeException("�������� �ʰų� ���� ������ �����ϴ�.");
		}
	}

	@Override
	public void insertComment(CommentDTO commentDTO) throws Exception {
		boardDAO.insertComment(commentDTO);
	}
	
	@Override
	public List<CommentDTO> getCommentList(CommentDTO commentDTO)
		throws Exception {

		return boardDAO.getCommentList(commentDTO);
	}

	@Override
	public void deleteComment(CommentDTO commentDTO) throws Exception {
		boardDAO.deleteComment(commentDTO);
	}
	
	@Override
	public void updateComment(CommentDTO commentDTO) throws Exception {
		if (boardDAO.updateComment(commentDTO) == 0) {
			throw new RuntimeException("��� ���� ����");
		}
	}
}
