package board;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BoardVO {
	private int boardno; //테이블컬럼명 브이오필드명 홈에잇는네임 똑같이
	private String title;
	private String content;
	private Timestamp regdate;
	//하나의 행ㅇ이니까 그행에 여러파일이들어가니까 배열로 들어감 
	//검색
	//페이지버호//조인 도ㄸㄹ다른 컬럼명
	//테이블필드명 말고 많은 필들들이 실제로 추가가된다
	
//	private FileVO[] filevo;
	
}
