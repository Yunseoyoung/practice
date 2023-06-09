package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class FilesDAO {
	private PreparedStatement pstmt;
	private Connection con;
	ResultSet rs;
	private DataSource dataFactory;
	
	public FilesDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource)envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		} //객체를 톰캣설정하고 그런걸 가져온다는거 알고잇어야 지우면안된다는걸 판단가능 
	}
	
	
	
	
//	목록
	public List<FilesVO> selectList(int boardno, String tablename) { //리스트 가져오는거 잇잖아
		List<FilesVO> list = new ArrayList<FilesVO>();
		
		try {
//			connDB();
			con = dataFactory.getConnection();
			String query = "select * from files where rel_pk=? and type=?";
			query += " order by fileno asc"; //먼저들어온거 맨 위로 하나의 글에대한 첨부파일 
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, boardno);
			pstmt.setString(2, tablename);
			rs = pstmt.executeQuery();
			
			while (rs.next()) { //결과값이잇으면하나씩담음
				FilesVO vo = new FilesVO();
				vo.setFileno(rs.getInt("fileno"));
				vo.setFilename_real(rs.getString("filename_real"));
				vo.setFilename_org(rs.getString("filename_org"));
				vo.setFilesize(rs.getLong("filesize"));
				vo.setRel_pk(rs.getInt("rel_pk"));
				vo.setType(rs.getString("type")); //출력할ㄹ때 쓰는컬럼아니라 굳이셋할필요없지남ㄴ 걍 싹다 담아서 리스트에 추가함
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();}catch(Exception e) {};
			try {pstmt.close();}catch(Exception e) {};
			try {con.close();}catch(Exception e) {};
		}
		return list;
	}
	
//	등록
	public boolean insert(FilesVO vo) { //무슨 메서드인지 보드브이오 객체입력받아서 디비에 등록하는 메서드임 첫줄만봐도 코드들 머릿속에 쫙 그려지게
		boolean result = false;
		try {
			con = dataFactory.getConnection();
			String query = "INSERT INTO files (";
			query += "fileno, filename_real, filename_org, filesize, rel_pk, type";
			query += ") VALUES (";
			query += "FILE_SEQ.NEXTVAL,?,?,?,?,?"; //줄바꿈은 가독성
			query += ")";
			pstmt = con.prepareStatement(query);
			int pstmtInt = 1;
			pstmt.setString(pstmtInt++, vo.getFilename_real()); 
			pstmt.setString(pstmtInt++, vo.getFilename_org()); 
			pstmt.setLong(pstmtInt++, vo.getFilesize());
			pstmt.setInt(pstmtInt++, vo.getRel_pk()); 
			pstmt.setString(pstmtInt++, vo.getType()); 
			int r = pstmt.executeUpdate();
			if (r > 0) result = true;
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public void delete(int fileno) { 
		
		try {
			con = dataFactory.getConnection();
			String query = "DELETE FROM files WHERE fileno=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, fileno);
			int r = pstmt.executeUpdate(); 
			System.out.println("삭제건수:"+r);
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
}
