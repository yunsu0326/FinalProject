package com.spring.app.saehan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.CommentVO;
import com.spring.app.domain.NoticeboardVO;
import com.spring.app.common.AES256;
import com.spring.app.common.FileManager;
import com.spring.app.domain.BoardVO;
import com.spring.app.saehan.model.SaehanDAO;


@Repository
@Service
public class SaehanService_imple implements SaehanService{

	
    @Autowired
 	private AES256 aES256;
 	
 	@Autowired  
	private FileManager fileManager;
	
	@Autowired 
	private SaehanDAO dao;
	
 
	// 총 게시물 건수(totalCount) 구하기 - 검색이 있을 때와 검색이 없을때 로 나뉜다.
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = dao.getTotalCount(paraMap);
		return n;
	}

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함 한 것)  
	@Override
	public List<BoardVO> boardListSearch_withPaging(Map<String, String> paraMap) {
		List<BoardVO> boardList = dao.boardListSearch_withPaging(paraMap);
		
		return boardList;
	}

	// 검색어 입력시 자동글 완성하기 
	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = dao.wordSearchShow(paraMap);
		return wordList;
	}
	
	//글쓰기(파일첨부가 없는 글쓰기) 
	@Override
	public int add(BoardVO boardvo) {
	// === 원글쓰기인지, 답변글쓰기인지 구분하기 시작 === //
		if("".equals(boardvo.getFk_seq())) {
			// 원글쓰기인 경우
			int groupno = dao.getGroupnoMax()+1;
			boardvo.setGroupno(String.valueOf(groupno));
		}	
	// === 원글쓰기인지, 답변글쓰기인지 구분하기 끝 === //
		int n = dao.add(boardvo);
		return n;
	}
	
	//글쓰기(파일첨부가 있는 글쓰기) 
	@Override
	public int add_withFile(BoardVO boardvo) {
		
		// === 원글쓰기인지, 답변글쓰기인지 구분하기 시작 === //
		if("".equals(boardvo.getFk_seq())) {
			// 원글쓰기인 경우
			int groupno = dao.getGroupnoMax()+1;
			boardvo.setGroupno(String.valueOf(groupno));
		}
		// === 원글쓰기인지, 답변글쓰기인지 구분하기 끝 === //
		int n = dao.add_withFile(boardvo); // 첨부파일이 있는 경우 
		return n;
	}
	
	// 글조회수 증가는 없고 단순히 글1개만 조회를 해주는 것
	@Override
	public BoardVO getView_no_increase_readCount(Map<String, String> paraMap) {
		BoardVO boardvo = dao.getView(paraMap); // 글1개 조회하기
		return boardvo;
	}
	
	//글 수정하기
	@Override
	public int edit(BoardVO boardvo) {
		int n = dao.edit(boardvo);
		return n;
	}
	
	//파일첨부가 있는 글 수정하기
	@Override
	public int edit_withFile(BoardVO boardvo) {
		int n = dao.edit_withFile(boardvo); // 첨부파일이 있는 경우 
		return n;
	}
	
	//글조회수 증가와 함께 글1개를 조회를 해주는 것 
	//(먼저, 로그인을 한 상태에서 다른 사람의 글을 조회할 경우에는 글조회수 컬럼의 값을 1증가 해야 한다.)
	@Override
	public BoardVO getView(Map<String, String> paraMap) {
				
		BoardVO boardvo = dao.getView(paraMap); // 글1개 조회하기
		String login_email = paraMap.get("login_email");
				
			if(login_email != null &&
			   boardvo != null &&
			   !login_email.equals(boardvo.getFk_email()) ) {
				// 글조회수 증가는 로그인을 한 상태에서 다른 사람의 글을 읽을때만 증가하도록 한다. 
				int n = dao.increase_readCount(boardvo.getSeq()); // 글조회수 1증가 하기 
					if(n==1) {
						boardvo.setReadCount(String.valueOf(Integer.parseInt(boardvo.getReadCount())+1) );
					}
			}
		return boardvo;
	}
	
	// 글 1개를 삭제하기
	@Override
	public int del(Map<String, String> paraMap) {
		
		int n = dao.del(paraMap);
		
		//파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 시작  //
		if(n==1) {
			String path = paraMap.get("path");
			String fileName = paraMap.get("fileName");
			
			if(fileName != null && !"".equals(fileName) ) {
				try {
					fileManager.doFileDelete(fileName, path);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		// === 파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 끝  === // 
		
		return n;
	}
	
	// 파일 삭제 1개를 삭제하기
    @Override
	public int delete_file(Map<String, String> paraMap, BoardVO boardvo) {
		
		int n = dao.delete_file(paraMap);
		
		//파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 시작  //
		if(n==1) {
			String path = paraMap.get("path");
			String fileName = paraMap.get("fileName");
			
			if(fileName != null && !"".equals(fileName) ) {
				try {
					fileManager.doFileDelete(fileName, path);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		// === 파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 끝  === // 
		
		return n;
	}
	
	
	
	
	
	
	// 원게시물에 딸린 댓글들을 페이징 처리해서 조회해오기
	@Override
	public int getCommentTotalPage(Map<String, String> paraMap) {
		int totalPage = dao.getCommentTotalPage(paraMap);
		return totalPage;
	}

	// 원게시물에 딸린 댓글들을 페이징 처리해서 조회해오기
	@Override
	public List<CommentVO> getCommentList_Paging(Map<String, String> paraMap) {
		List<CommentVO> commentList = dao.getCommentList_Paging(paraMap);
		return commentList;
	}

	// 댓글쓰기(transaction 처리)
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int addComment(CommentVO commentvo) {
		int n1=0,n2=0;
			
		n1 = dao.addComment(commentvo); // 댓글쓰기(tbl_comment 테이블에 insert)
			
			if(n1 == 1) { 
				n2 = dao.updateCommentCount(commentvo.getParentSeq()); // tbl_board 테이블에 commentCount 컬럼이 1증가(update) 
			}
			
			return n2;
	}
		
	//원래 적혀있던 댓글 내용 불러오기
	@Override
	public CommentVO getComment_One(Map<String, String> paraMap) {
		CommentVO commentvo = dao.getComment_One(paraMap); 
		return commentvo;
	}
		
	
	// 댓글 수정하기
	@Override
	public int getupdate_review(Map<String, String> paraMap, CommentVO commentvo) {
		int n = dao.getupdate_review("saehan.getupdate_review", paraMap);
		return n;
	}
	
	
	//댓글 파일이름 조회하기
	@Override
	public CommentVO getView_comment(Map<String, String> paraMap) {
		CommentVO commentvo = dao.getView_comment(paraMap); 
		return commentvo;
	}
	
	
	// 파일첨부가 되어진 댓글 1개에서 서버에 업로드되어진 파일명과 오리지널파일명을 조회해주는 것  
	@Override
	public CommentVO getCommentOne(String seq) {
		CommentVO commentvo = dao.selectOne("saehan.getCommentOne", seq);
		return commentvo;
	}
	

	
	//원게시물에 딸린 댓글들을 조회해오기
	@Override
	public List<CommentVO> getCommentList(String parentSeq) {
		List<CommentVO> commentList = dao.getCommentList(parentSeq);
		return commentList;
	}


	//첨부 파일있는 댓글 삭제하기
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int del_comment(Map<String, String> paraMap, CommentVO commentvo) {
		int n1=0 , n2=0;
		n1 = dao.del_comment(paraMap);
			
		// ===== 파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 시작  ===== //
		if(n1==1) {
			String path = paraMap.get("path");
			String fileName = paraMap.get("fileName");
			n2 = dao.minusCommentCount(commentvo.getParentSeq());
			if( fileName != null && !"".equals(fileName) ) {
				try {
					fileManager.doFileDelete(fileName, path);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		// === 파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 끝  === // 
			
		return n2;
	}


	//첨부 파일 없는 댓글 삭제하기
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int del_comment_nofile(Map<String, String> paraMap, CommentVO commentvo) {
		int n1=0,n2=0;
			
		n1 = dao.del_comment_nofile(paraMap); // 댓글쓰기(tbl_free_comment 테이블에 insert)
			
		if(n1 == 1) { 
			n2 = dao.minusCommentCount(commentvo.getParentSeq()); // tbl_freeboard 테이블에 commentCount 컬럼이 1증가(update) 
		}
		return n2;
	}

	
	//공지사항의 총 게시물 건수(totalCount) 구하기 - 검색이 있을 때와 검색이 없을때 로 나뉜다.
	@Override
	public int getNoticeTotalCount(Map<String, String> paraMap) {
		int n = dao.getNoticeTotalCount(paraMap);
		return n;
	}

	// 페이징 처리한 공지사항 목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함 한 것)  
	@Override
	public List<NoticeboardVO> noticeListSearch_withPaging(Map<String, String> paraMap) {
		List<NoticeboardVO> boardList = dao.noticeListSearch_withPaging(paraMap);
		return boardList;
	}
	
	//공지사항 글쓰기(파일첨부가 없는 공지사항글쓰기) 
	@Override
	public int notice_add(NoticeboardVO boardvo) {
		int n = dao.notice_add(boardvo);
		return n;
	}
	

	//글쓰기(파일첨부가 있는 글쓰기) 
	@Override
	public int notice_add_withFile(NoticeboardVO boardvo) {
		int n = dao.notice_add_withFile(boardvo); // 첨부파일이 있는 경우 
		return n;
	}
	
	//공지사항 글을 읽었을 때 조회수가 1 늘게끔 만들기
	@Override
	public NoticeboardVO getNoticeView(Map<String, String> paraMap) {
		NoticeboardVO boardvo = dao.getNoticeView(paraMap); // 공지사항 1개 조회하기
		String login_Employee_id = paraMap.get("login_Employee_id");
				
			if(login_Employee_id != null &&
			   boardvo != null &&
			   !login_Employee_id.equals(boardvo.getFk_emp_id()) ) {
				// 글조회수 증가는 로그인을 한 상태에서 다른 사람의 글을 읽을때만 증가하도록 한다. 
				int n = dao.notice_increase_readCount(boardvo.getSeq()); // 글조회수 1증가 하기 
					if(n==1) {
						boardvo.setRead_Count(String.valueOf(Integer.parseInt(boardvo.getRead_Count())+1) );
					}
			}
		return boardvo;
		
	}

	//공지사항 글을 읽었을 때 조회수가 안 늘게끔 만들기
	@Override
	public NoticeboardVO getnotice_View_no_increase_readCount(Map<String, String> paraMap) {
		NoticeboardVO boardvo = dao.getNoticeView(paraMap); // 글1개 조회하기
		return boardvo;
	}
	
	
	// 공지사항 1개를 삭제하기
	@Override
	public int notice_del(Map<String, String> paraMap) {
			
		int n = dao.notice_del(paraMap);
			
		//파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 시작  //
		if(n==1) {
			String path = paraMap.get("path");
			String file_Name = paraMap.get("file_Name");
				
			if(file_Name != null && !"".equals(file_Name) ) {
				try {
					fileManager.doFileDelete(file_Name, path);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
			// === 파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 끝  === // 
			
		return n;
	}
	
	//공지사항 수정하기
	@Override
	public int notice_edit(NoticeboardVO boardvo) {
		int n = dao.notice_edit(boardvo);
		return n;
	}

	// 공지사항 검색어 입력시 자동글 완성하기 
	@Override
	public List<String> notice_wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = dao.notice_wordSearchShow(paraMap);
		return wordList;
	}
	
	//공지사항 파일 삭제하기 
	@Override
	public int notice_delete_file(Map<String, String> paraMap, NoticeboardVO boardvo) {
		int n = dao.notice_delete_file(paraMap);
		
		//파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 시작  //
		if(n==1) {
			String path = paraMap.get("path");
			String file_Name = paraMap.get("file_Name");
			
			if(file_Name != null && !"".equals(file_Name) ) {
				try {
					fileManager.doFileDelete(file_Name, path);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		// === 파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 끝  === // 
		
		return n;
	}

	@Override
	public int notice_edit_withFile(NoticeboardVO boardvo) {
		int n = dao.notice_edit_withFile(boardvo); // 첨부파일이 있는 경우 
		return n;
	}

	

	
	

}
	


