package kr.co.kosa.article.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository()
public class ArticleDAOOracle implements IArticleDAO {
	private static Logger logger = LoggerFactory.getLogger(ArticleDAOOracle.class);
	
//	private static IArticleDAO articleDAO = null;
	
	public ArticleDAOOracle() {	// Data access object
		try {
			Class.forName("net.sf.log4jdbc.DriverSpy");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:log4jdbc:oracle:thin:@localhost:1521:xe","spring","spring");
	}	
	
//	public static IArticleDAO getInstance() {
//		if (articleDAO == null) {
//			articleDAO = new ArticleDAOOracle();
//		}
//		return articleDAO;
//	}

	@Override
	public void insertArticle(ArticleDTO articleDTO) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into t_board(no, title, name, passwd, content)");	
		sql.append(" values(t_board_no_seq.nextval, ?, ?, ?, ?)");
		
		try(Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
			pstmt.setString(1, articleDTO.getTitle());
			pstmt.setString(2, articleDTO.getName());
			pstmt.setString(3, articleDTO.getPasswd());
			pstmt.setString(4, articleDTO.getContent());
			for(int i=1; i <= 1000; i++) {
				pstmt.executeUpdate();
			}
		}
	}

//	@Override
//	public List<ArticleDTO> getArticleList(PageDTO pageDTO) throws Exception {
//		// TODO Auto-generated method stub
//		StringBuffer sql = new StringBuffer();
//		sql.append(" SELECT B.*");
//		sql.append(" FROM(SELECT rownum as rnum,A.*    ");
//		sql.append("      FROM     (select no, title, name,");
//		sql.append("                to_char(regdate,'YYYY-MM-DD') as regdate, readcount");
//		sql.append("                from   board");
//		sql.append("                order  by no desc) A) B");
//		sql.append(" where  rnum between (&thisPage-1)*&pageSize+1");
//		sql.append(" and    &thisPage*&pageSize;");
//		
//		
//		List<ArticleDTO> list = new ArrayList<>();
//		try(Connection conn = getConnection();
//			 PreparedStatement pstmt = conn.prepareStatement(sql.toString());
//			 pstmt.setLong(1, pageDTO.getStartNum());
//			 pstmt.setLong(2, pageDTO.getEndNUm())){
//			try(ResultSet rs = pstmt.executeQuery()){
//				while(rs.next()) {
//					ArticleDTO articleDTO = new ArticleDTO();
//					articleDTO.setNo(rs.getLong("no"));
//					articleDTO.setTitle(rs.getString("title"));
//					articleDTO.setName(rs.getString("name"));
//					articleDTO.setRegdate(rs.getString("regdate"));
//					articleDTO.setReadcount(rs.getInt("readcount"));
//					list.add(articleDTO);
//				}
//			}
//		}
//		
//		return list;
//	}

	@Override
	public ArticleDTO getArticle(long no) throws Exception {
		// TODO Auto-generated method stub
		ArticleDTO articleDTO = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select no, ");
		sql.append(" 	    title, ");
		sql.append(" 	    name, ");
		sql.append(" 	    regdate,");
		sql.append(" 	    content,");
		sql.append(" 	    readcount ");
		sql.append(" from t_board");
		sql.append(" where no=?");
		
		try(Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
			pstmt.setLong(1, no);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next())
					articleDTO = new ArticleDTO();
					articleDTO.setNo(rs.getLong("no"));
					articleDTO.setTitle(rs.getString("title"));
					articleDTO.setName(rs.getString("name"));
					articleDTO.setRegdate(rs.getString("regdate")); 
					articleDTO.setReadcount(rs.getInt("readcount"));
					articleDTO.setContent(rs.getString("content"));
			}
		}
		return articleDTO;
	}

	@Override
	public void updateArticle(ArticleDTO articleDTO) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append(" update t_board set");
		sql.append("        name=?");
		sql.append("       ,title=?");
		sql.append("       ,content=?");
		sql.append(" where  no=?");
		sql.append("   and  passwd=?");
		
		try(Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
			
			pstmt.setString(1, articleDTO.getName());
			pstmt.setString(2, articleDTO.getTitle());
			pstmt.setString(3, articleDTO.getContent());
			pstmt.setLong(4, articleDTO.getNo());
			pstmt.setString(5, articleDTO.getPasswd());
			
			if(pstmt.executeUpdate() == 0) {	// ����� Ʋ�� ���
				throw new RuntimeException("��й�ȣ�� Ʋ���ϴ�!!!");
			} 
			
		}
		
	}

	@Override
	public void deleteArticle(ArticleDTO articleDTO) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append(" delete t_board");
		sql.append("      where no = ?");
		sql.append("      and");
		sql.append("      passwd = ?");

		
		try(Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
			
			pstmt.setLong(1, articleDTO.getNo());
			pstmt.setNString(2, articleDTO.getPasswd());
			
			if(pstmt.executeUpdate()==0) {
				throw new RuntimeException("��й�ȣ�� Ʋ���ϴ�!");
			}
		}
	}

	@Override
	public List<ArticleDTO> getArticleList(PageDTO pageDTO) throws Exception 
	{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT B.*");
		sql.append(" FROM(SELECT rownum as rnum,A.*    ");
		sql.append("      FROM     (select no, title, name,");
		sql.append("                to_char(regdate,'YYYY/MM/DD') as regdate, readcount");
		sql.append("                from   t_board");
		sql.append("                order  by no desc) A) B");
		sql.append(" where rnum between ? and ?");
		
		
		List<ArticleDTO> list = new ArrayList<>();
		
		try(Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
			pstmt.setLong(1, pageDTO.getStartNum());
			pstmt.setLong(2, pageDTO.getEndNum());
		{
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					ArticleDTO articleDTO = new ArticleDTO();
					articleDTO.setNo(rs.getLong("no"));
					articleDTO.setTitle(rs.getString("title"));
					articleDTO.setName(rs.getString("name"));
					articleDTO.setRegdate(rs.getString("regdate"));
					articleDTO.setReadcount(rs.getInt("readcount"));
					list.add(articleDTO);
				}
			}
		}
			
		return list;
	}
}

	@Override
	public long getTotalArticleCount() throws Exception {
      StringBuffer sql = new StringBuffer();
      sql.append(" select count(*) as cnt");
      sql.append(" from   t_board");
      
      try(Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
         try(ResultSet rs = pstmt.executeQuery()){
            if(rs.next()) {
               return rs.getLong("cnt");
            }else {
               throw new RuntimeException("���ڵ� ���� �������µ� �����߽��ϴ�.");
            }
        }
      }
	}

	@Override
	public void updateReadCount(long no) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append(" update t_board set");
		sql.append("        readcount = readcount + 1");
		sql.append(" where no=?");
		
		try(Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
			
			pstmt.setLong(1,  no);
			if(pstmt.executeUpdate() != 1) {
				throw new RuntimeException("");
			}
		}
	}
}
