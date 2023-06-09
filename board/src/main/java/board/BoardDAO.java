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

public class BoardDAO {
	private PreparedStatement pstmt;
	private Connection con;
	ResultSet rs;
	private DataSource dataFactory;
	
	public BoardDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource)envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		} //객체를 톰캣설정하고 그런걸 가져온다는거 알고잇어야 지우면안된다는걸 판단가능 
		//case sensitive 체크해야됨 대소문자 같이 바뀔수잇어서 싹다 바꾸고
		
	}
	
	
	//보드 브이오를 제네릭으로 선언한 리스트로 리턴할거니까
	// 멤버 디에이오도 알고잇다는 ㅓㄴ제 
	//추가 삭제 로그인 중복체크 메서드 알고잇어야함
	//리스트멤버는 겍체담아서 리턴하는메서드라고 알고잇잖아
	//알고잇으니까 조금 비ㅏ꿔서 쓴느거잖아
	
	
	
	
//	목록
	public List<BoardVO> selectList(String searchWord) { //리스트 가져오는거 잇잖아
		//아웃라인으로 봐바 
		List<BoardVO> list = new ArrayList<BoardVO>();
		
		try {
//			connDB();
			con = dataFactory.getConnection();
			String query = "select * from board";
			if (searchWord != null && !"".equals(searchWord)) {
				query += " where title like '%"+searchWord+"%' or content like '%"+searchWord+"%' ";
			}
			query += " order by boardno desc"; //보드엔오는 최신이니까 계속위로 올라오는? 지금은 상관없다 날짜?
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) { //결과값이잇으면하나씩담음
				//회사가면 등록일 날짜 입력받고 사용자가 관리자에서 직접 날짜 시간까지 등록하게돼잇음
				//관리자에서 등록할때 날짜 입력할수잇는 기능
				BoardVO vo = new BoardVO();
				vo.setBoardno(rs.getInt("boardno"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setRegdate(rs.getTimestamp("regdate"));
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
	public boolean insert(BoardVO vo) { //무슨 메서드인지 보드브이오 객체입력받아서 디비에 등록하는 메서드임 첫줄만봐도 코드들 머릿속에 쫙 그려지게
		//보드브이오에 넣어줌 리턴할수잇지만
		boolean result = false;
		try {
			con = dataFactory.getConnection();
			String query = "INSERT INTO board (boardno, title, content, regdate) VALUES (BOARD_SEQ.NEXTVAL,?,?,SYSDATE)";
			//바꾸게할수도잇고 자동으로 들어가게할수도잇는거 날짜
			//두개의 멀 채워야함
			pstmt = con.prepareStatement(query);
			int pstmtInt = 1;
			pstmt.setString(pstmtInt++, vo.getTitle()); //여기에 뭘써야하는지모르면 객체나 자바에대한 기본문법 사용법을 모르고잇는거니까 많이 연습해라
			pstmt.setString(pstmtInt++, vo.getContent());
			int r = pstmt.executeUpdate();
			if (r > 0) result = true;
			
			
			
			
			
			pstmt = con.prepareStatement("SELECT BOARD_SEQ.CURRVAL AS currval FROM dual"); //시퀀스의 현재값나옴
			rs = pstmt.executeQuery();
			rs.next();
			int boardno = rs.getInt("currval");//컬럼명지정하려고 
			vo.setBoardno(boardno);
			
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//crud는 거의대부분잇어서 카피해놓는게 편함
//	삭제
	public void delete(int boardno) { //pk 가 이미잇다 정수써도되고 피케이가 넘어올거고  String 해도 아무상관없어 파스인트 변환안해줘도되ㅐㅁ
		try {
			con = dataFactory.getConnection();
			String query = "DELETE FROM board WHERE boardno=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, boardno);
			int r = pstmt.executeUpdate(); //삭제건수 리턴 0보다 크면 트루 아님 폴스해도됨
			System.out.println("삭제건수:"+r);
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	상세 보드 브이오 넘겨줘야??? 10번피케이에대한값은 보드브이오에 담아야함 보드엔오 넘겨줘야 보드엔오넣잔아 보드엔오라고 안써도딤 바깥이름과 상관없어 걍 값이 들어가는것뿐
	//변수는 이름으링ㄹ일뿐이다
	//카피할수잇으면 모조리 카피 일할때
	//공부할때는 해봐도되고
	public BoardVO selectOne(int boardno) {
		//보드브이오 왜선언햇냐고 코딩하다보면 안에 하면 리턴이안돼 블럭안에잇으면 그래서 선언해놓고 들어가
		
		BoardVO vo = new BoardVO(); //객체생ㅅ어됨 
		Statement stmt =null;
		try {//에스큐엘실행실행결과 담아서 브이오 결과를 리턴해
			con = dataFactory.getConnection();
			//한건조회는 프리페어안써도되고 보안적으로중요도아니고 조회하는거니까 ㅏㅇ과상관없어
			stmt = con.createStatement();	
			rs = stmt.executeQuery("SELECT * FROM board WHERE boardno="+boardno);
			//보드엔오가 1부터 10인데 20넘어오면 안될수도
			if(rs.next()) {
				//카운트잇을수도없을수도
				//잇으면 보드비브이오 담고 객체 생성
				//셋은 객체에다 뭘 값을 ㄱ바꾸는거지 
				//이미 값이 다들어잇어
				//보드에는 0으로 타이틀은
				//여기다놓으면 항상 중괗롷안에서쓸수잇으니까 바깥에 써라
				//여기해놓고 잇으면 뉴해서 해도되고
				vo.setBoardno(rs.getInt("boardno"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setRegdate(rs.getTimestamp("regdate"));
				//한건 조회해서 vo에 붙는거
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch(Exception e ) {}
			try {stmt.close();} catch(Exception e ) {}
			//모든 스코프는 범위가 중괄호
		}
		return vo;
	}
	//수정 처리하는거
	//컨트롤러에서 브이오에 담아 넘겨줄거야 사용자가 입력한값ㅇ들을
	//싹 담아서 넘겨줄거야
	public void update(BoardVO vo) { // 내용 수정해야돼서 boardvo 수정하남 보드브이오에 제목내용잇어서?
		try {
			con = dataFactory.getConnection(); //보드엔오 수정안하는게 아 그건 피케이니까? 
			//타이ㅡㄹ과비교해서 같으면 동적으로이픔ㄴ선언해서 타이틀 컨텐트안바뀌는머그런
			//바꾸던 안바꾸던 업뎃치면 안바뀐게 업뎃쳐지는거라 신경쓸필요없어
			
			String query = "UPDATE board SET title=?, content=? WHERE boardno=?"; //맨뒤에잇어서 삼번에다 보드엔오
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getBoardno());
			int r = pstmt.executeUpdate(); //삭제건수 리턴 0보다 크면 트루 아님 폴스해도됨
			pstmt.close(); //실행안되니까 파이널리에넣어라? 중간에 중지된다는건 에스큐엘 잘못짯다는거다 어차피 재시작?
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
