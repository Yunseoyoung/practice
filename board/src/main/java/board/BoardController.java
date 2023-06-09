package board;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("*.do")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	private void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String url = req.getRequestURI().substring(req.getContextPath().length());
		
		//변수로 포워딩할 경로를 담을 
		String page = "";
		
		
	
		
		if ("/board/index.do".equals(url)) {//목록
			BoardDAO dao = new BoardDAO();
			req.setAttribute("boardList", dao.selectList(req.getParameter("searchWord")));
			//안쪽부터 
			
			page = "/WEB-INF/view/board/index.jsp";
		} else if ("/board/write.do".equals(url)) { //쓰기 등록폼 //제목내용파일첨부한거 3개정도해서 저장시키고 보드에만저장 
			//파일디에이오아직안만들어서 보드에 저장하는것부터먼저
			
			page = "/WEB-INF/view/board/write.jsp";
			
			
		} else if ("/board/view.do".equals(url)) { //상세
			BoardDAO dao = new BoardDAO();
			BoardVO vo = dao.selectOne(Integer.parseInt(req.getParameter("boardno")));//파라미터받아서 정수로 바꾸고 셀렉원메서드 행이브이오객체로 들어가
			req.setAttribute("data", vo);
			req.setAttribute("fileList", new FilesDAO().selectList(vo.getBoardno(), "board")); //리스트갖고오면 이이름으로 저장되고
			page = "/WEB-INF/view/board/view.jsp";
		} else if ("/board/edit.do".equals(url)) { //수정폼
			BoardDAO dao = new BoardDAO();
			BoardVO vo = dao.selectOne(Integer.parseInt(req.getParameter("boardno")));//파라미터받아서 정수로 바꾸고 셀렉원메서드 행이브이오객체로 들어가
			req.setAttribute("data", vo);
			req.setAttribute("fileList", new FilesDAO().selectList(vo.getBoardno(), "board"));
			page = "/WEB-INF/view/board/edit.jsp";
			
			
			// 밑에 세개는 처리하는거
		} else if ("/board/insert.do".equals(url)) { //등록 라이트점두에서입력하고 저장하면 이리로 옴
			MultipartRequest multi = new MultipartRequest(req, req.getRealPath("/upload"), 1024*1024, "utf-8", new DefaultFileRenamePolicy());
			BoardVO vo = new BoardVO();
			vo.setTitle(multi.getParameter("title"));
			vo.setContent(multi.getParameter("content"));
			
			// dao에 있는 insert 메서드 실행 시킴 
			BoardDAO dao = new BoardDAO();
			
			boolean r = dao.insert(vo); //true리턴 //디비가서 보면 데이터가 날짜로잘들어감
			System.out.println(r + ":" +vo.getBoardno()); //title content제대로 저장됏는지
			
			// 처리 후 메시지, 이동할 경로 저장할 변수
			String msg = "";
			String move = "";
			
			// 파일 등록
			if (r) {
				FilesVO fvo = new FilesVO();
				fvo.setRel_pk(vo.getBoardno());
				fvo.setType("board");
				
				// 첨부파일 처리
				Enumeration files = multi.getFileNames();
				FilesDAO fdao = new FilesDAO();
				while(files.hasMoreElements()) {
					String name = (String)files.nextElement();
					String real = multi.getFilesystemName(name);
					String org = multi.getOriginalFileName(name);
					long size = multi.getFile(name).length();
					
					fvo.setFilename_real(real);
					fvo.setFilename_org(org);
					fvo.setFilesize(size);
					
					fdao.insert(fvo);
					
					
				}
				msg = "정상적으로 등록되었습니다.";
				move = "index.do"; 
				
			} else {
				msg = "등록실패";
				move = "write.do"; 
			}
			req.setAttribute("msg", msg);
			req.setAttribute("move", move);
			
			
			page = "/WEB-INF/view/common/alert.jsp";//브라우저입장에서 보드의 인서트점 두 임 경로 
			
		} else if ("/board/update.do".equals(url)) { //수정 처리
			BoardDAO dao = new BoardDAO();
			BoardVO vo = new BoardVO();
			MultipartRequest multi = new MultipartRequest(req, req.getRealPath("/upload"), 1024*1024, "utf-8", new DefaultFileRenamePolicy());
			vo.setBoardno(Integer.parseInt(multi.getParameter("boardno"))); //멀티파트로전송하면이렇게못받음 멀티파트객체로 받던가 톰캣 다른라이브러리로햇을때 그렇게 받던가 그 객체로 받아야지 그냥 리퀘로 값못받음 다른데로 가져가서 널이 튀어나옴 에러나 아까처럼 멀티로
			vo.setTitle(multi.getParameter("title"));
			vo.setContent(multi.getParameter("content"));
			dao.update(vo);
			req.setAttribute("msg", "정상적으로 수정되었습니다.");
			req.setAttribute("move", "view.do?boardno="+vo.getBoardno());//자바스크립트에서위치로이동시ㅕ켜줌
			
			// dao에 있는 insert 메서드 실행 시킴 
			
			
			boolean r = dao.insert(vo); //true리턴 //디비가서 보면 데이터가 날짜로잘들어감
			System.out.println(r + ":" +vo.getBoardno()); //title content제대로 저장됏는지
			
			// 처리 후 메시지, 이동할 경로 저장할 변수
			String msg = "";
			String move = "";
			
			// 파일 등록
						if (r) {
							FilesVO fvo = new FilesVO();
							fvo.setRel_pk(vo.getBoardno());
							fvo.setType("board");
							
							// 첨부파일 처리
							Enumeration files = multi.getFileNames();
							FilesDAO fdao = new FilesDAO();
							while(files.hasMoreElements()) {
								String name = (String)files.nextElement();
								String real = multi.getFilesystemName(name);
								String org = multi.getOriginalFileName(name);
								long size = multi.getFile(name).length();
								
								fvo.setFilename_real(real);
								fvo.setFilename_org(org);
								fvo.setFilesize(size);
								
								fdao.insert(fvo);
								
								
							}
							msg = "정상적으로 등록되었습니다.";
							move = "index.do"; 
							
						} else {
							msg = "등록실패";
							move = "write.do"; 
						}
						req.setAttribute("msg", msg);
						req.setAttribute("move", move);
			
			
			
			
			
			
			page = "/WEB-INF/view/common/alert.jsp"; //얼럿띄우고 이동시키는ㅋ드가안에잇다
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		} else if ("/board/delete.do".equals(url)) { //글삭제
			BoardDAO dao = new BoardDAO();
			dao.delete(Integer.parseInt(req.getParameter("boardno")));
			res.getWriter().println("ok"); //삭제여부상관없이 오케이 출력
			//오케이 받으면 정상적으로 삭제됐습니다 출력
		} else if ("/board/deleteFile.do".equals(url)) { //ㅍ파일삭제
			FilesDAO dao = new FilesDAO();
			dao.delete(Integer.parseInt(req.getParameter("fileno")));
			req.setAttribute("result", "ok");
			page="/WEB-INF/view/common/result.jsp";
		}
		
		if(page.startsWith("redirect:")) {
			res.sendRedirect(page.substring(9)); //9부터 끝까지 //경로로 보내버리고
		} else {
			req.getRequestDispatcher(page).forward(req, res); //그렇ㄱ징낳으면 포워딩
			
		}
		
		//슬러시 보드 뒤부터 쭉나오니까 보드의 라이트점두 보드는 프ㄹ젝명ㅇ이고
		System.out.println(url); //컨택스트 패스를 빼고 일단가져옴
		//슬러시 보드는 컨택스트패스가 아니잖아 //컨택스트 패스를 빼야 다른프로젝트가더라도 신경쓸필요없어서 그렇게함
	}
	
	

}
