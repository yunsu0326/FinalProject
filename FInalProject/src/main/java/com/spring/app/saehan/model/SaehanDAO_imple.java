package com.spring.app.saehan.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.CommentVO;
import com.spring.app.domain.NoticeboardFileVO;
import com.spring.app.domain.NoticeboardVO;
import com.spring.app.domain.BoardFileVO;
import com.spring.app.domain.BoardVO;

@Repository
public class SaehanDAO_imple implements SaehanDAO{

	
	@Resource
	private SqlSessionTemplate sqlsession;
	
	
	// 총 게시물 건수(totalCount) 구하기 - 검색이 있을 때와 검색이 없을때 로 나뉜다.
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("saehan.getTotalCount", paraMap);
		return n;
	}


    // 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함 한 것)  
	@Override
	public List<BoardVO> boardListSearch_withPaging(Map<String, String> paraMap) {
		List<BoardVO> boardList = sqlsession.selectList("saehan.boardListSearch_withPaging", paraMap);
		return boardList;
	}

	
	// 검색어 입력시 자동글 완성하기 
	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = sqlsession.selectList("saehan.wordSearchShow", paraMap);
		return wordList;
	}
	
	//답글쓰기에 필요한 값 가져오기
	@Override
	public int getGroupnoMax() {
		int maxgrouno = sqlsession.selectOne("saehan.getGroupnoMax");
		return maxgrouno;
	}
	
	// 글쓰기(파일첨부가 없는 글쓰기)
	@Override
	public int add_nofile(BoardVO boardvo) {
		int n = sqlsession.insert("saehan.add_nofile", boardvo);
		return n;
	}
	
	// 글쓰기(파일첨부가 있는 글쓰기)
	@Override
	public int add_withFile(BoardVO boardvo) {
		int n = sqlsession.insert("saehan.add_withFile", boardvo);
		return n;
	}
	
	//글 조회수 증가는 없고 단순히 글 1개만 조회를 해주는 것
	@Override
	public BoardVO getView(Map<String, String> paraMap) {
		BoardVO boardvo = sqlsession.selectOne("saehan.getView", paraMap);
		return boardvo;
	}
	
	//글 수정하기
	@Override
	public int edit(BoardVO boardvo) {
		int n = sqlsession.update("saehan.edit", boardvo);
		return n;
	}
	
	//파일첨부가 있는 글 수정하기
	@Override
	public int edit_withFile(BoardVO boardvo) {
		int n = sqlsession.update("saehan.edit_withFile", boardvo);//첨부파일이 있는 경우
		return n;
	}
	
	//글에 있는 첨부파일 1개 삭제하기
	@Override
	public int delete_file(Map<String, String> paraMap) {
		int n = sqlsession.update("saehan.delete_file", paraMap);
		return n;
	}
	
	//글의 조회수 1 증가 하기 
  	@Override
  	public int increase_readCount(String seq) {
  		int n = sqlsession.update("saehan.increase_readCount", seq);
  		return n;
  	}
  	
	// 1개글 삭제하기 
    @Override
	public int del(Map<String, String> paraMap) {
		int n = sqlsession.delete("saehan.del", paraMap);
		return n;
	}

    // 원글 글번호(parentSeq)에 해당하는 댓글의 totalPage 수 알아오기
 	@Override
 	public int getCommentTotalPage(Map<String, String> paraMap) {
 		int totalPage = sqlsession.selectOne("saehan.getCommentTotalPage", paraMap);
 		return totalPage;
 	}
    
	// 원게시물에 딸린 댓글들을 페이징 처리해서 조회해오기
	@Override
	public List<CommentVO> getCommentList_Paging(Map<String, String> paraMap) {
		List<CommentVO> commentList = sqlsession.selectList("saehan.getCommentList_Paging", paraMap);
		return commentList;
	}
	
	// 댓글쓰기(transaction 처리)
	@Override
	public int addComment(CommentVO commentvo) {
		int n = sqlsession.insert("saehan.addComment", commentvo);
		return n;
	}
	
	// 댓글을 쓰면 tbl_freeboard 테이블에 commentCount 컬럼이 1증가(update)
	@Override
	public int updateCommentCount(String parentSeq) {
		int n = sqlsession.update("saehan.updateCommentCount", parentSeq);
		return n;
	}

	// 파일첨부가 되어진 댓글 1개에서 서버에 업로드되어진 파일명과 오리지널파일명을 조회해주는 것  
	@Override
	public CommentVO selectOne(String string, String seq) {
		CommentVO commentvo = sqlsession.selectOne("saehan.getCommentOne", seq);
		return commentvo;
	}
	
	
	//원게시물에 딸린 댓글들을 조회해오기
	@Override
	public List<CommentVO> getCommentList(String parentSeq) {
		List<CommentVO> commentList = sqlsession.selectList("saehan.getCommentList", parentSeq);
		return commentList;
	}

	// 원래 적혀있던 댓글 내용 불러오기
	@Override
	public CommentVO getComment_One(Map<String, String> paraMap) {
		CommentVO commentvo = sqlsession.selectOne("saehan.getComment_One", paraMap);
		return commentvo;
	}
	
	//댓글을 수정하기 
	@Override
	public int getupdate_review(String string, Map<String, String> paraMap) {
		int n = sqlsession.update("saehan.getupdate_review", paraMap);
		return n;
	}
	
	//파일첨부가 되어진 댓글 1개에서 서버에 업로드되어진 파일명과 오리지널파일명을 조회해주는 것  
	@Override
	public CommentVO getView_comment(Map<String, String> paraMap) {
		CommentVO commentvo = sqlsession.selectOne("saehan.getView_comment", paraMap);
		return commentvo;
	}
	
	//첨부파일 있는 댓글 삭제하기
	@Override
	public int del_comment(Map<String, String> paraMap) {
		int n = sqlsession.delete("saehan.del_comment", paraMap);
		return n;
	}

	//첨부 파일 없는 댓글 삭제
	@Override
	public int del_comment_nofile(Map<String, String> paraMap) {
		int n = sqlsession.delete("saehan.del_comment_nofile", paraMap);
		return n;
	}

    // tbl_freeboard 테이블에 commentCount 컬럼이 1 감소(update) 
	@Override
	public int minusCommentCount(String parentSeq) {
		int n = sqlsession.update("saehan.minusCommentCount", parentSeq);
		return n;
	}

	//공지사항의 총 게시물 건수(totalCount) 구하기 - 검색이 있을 때와 검색이 없을때 로 나뉜다.
	@Override
	public int getNoticeTotalCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("saehan.getNoticeTotalCount", paraMap);
		return n;
	}

	
	//페이징 처리한 공지사항 목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함 한 것)  
	@Override
	public List<NoticeboardVO> noticeListSearch_withPaging(Map<String, String> paraMap) {
		List<NoticeboardVO> boardList = sqlsession.selectList("saehan.noticeListSearch_withPaging", paraMap);
		return boardList;
	}

	//첨부파일 있는 공지사항 쓰기
	@Override
	public int notice_add(NoticeboardVO boardvo) {
		int n = sqlsession.insert("saehan.notice_add", boardvo);
		return n;
	}

	
	

    //공지사항 글 1개 조회하기 
	@Override
	public NoticeboardVO getNoticeView(Map<String, String> paraMap) {
		NoticeboardVO boardvo = sqlsession.selectOne("saehan.getNotice_View", paraMap);
		return boardvo;
	}

	//공지사항 글 조회수 1 증가하기
	@Override
	public int notice_increase_readCount(String seq) {
		int n = sqlsession.update("saehan.notice_increase_readCount", seq);
  		return n;
	}

	//공지사항 글 1개 삭제하기
	@Override
	public int notice_del(Map<String, String> paraMap) {
		int n = sqlsession.delete("saehan.notice_del", paraMap);
		return n;
	}

	//공지사항 글 1개 수정하기
	@Override
	public int notice_edit(NoticeboardVO boardvo) {
		int n = sqlsession.update("saehan.notice_edit", boardvo);
		return n;
	}


	@Override
	public List<String> notice_wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = sqlsession.selectList("saehan.notice_wordSearchShow", paraMap);
		return wordList;
	}

	//공지사항에 있는 첨부파일 삭제하기
	@Override
	public int notice_delete_file(String fileno) {
		int n = sqlsession.delete("saehan.notice_delete_file", fileno);
		return n;
	}


	@Override
	public int notice_edit_withFile(NoticeboardVO boardvo) {
		int n = sqlsession.update("saehan.notice_edit_withFile", boardvo);
		return n;
	}

/*
	@Override
	public int add_withMultiFilet(BoardFileVO boardvo) {
		int n = sqlsession.update("saehan.add_withMultiFilet", boardvo);
		return n;
	}
*/
	// 자유게시판 글번호 알아오기
	@Override
	public String getfreeBoardSeq() {
		String seq = sqlsession.selectOne("saehan.getfreeboardSeq");
		return seq;
	}
	
	// 공지사항 글번호 알아오기
	@Override
	public String getNoitceBoardSeq() {
		String seq = sqlsession.selectOne("saehan.getNoitceboardSeq");
		return seq;
	}
	

	@Override
	public int insertFiles(List<BoardFileVO> fileList) {
		int n = sqlsession.insert("saehan.insertFiles", fileList);
		return n;
	}

	// 글 작성하기
	@Override
	public int addEnd(BoardVO boardvo) {
		int n = sqlsession.insert("saehan.addEnd", boardvo);
		return n;
	}



	
	// 첨부파일 목록 조회(글 상세 조회)
		@Override
		public List<BoardFileVO> getView_files(String seq) {
			return sqlsession.selectList("saehan.getView_files", seq);
		}
	/*
	@Override
	public int getTake_seq(Map<String, String> paraMap) {
		int chabun = sqlsession.selectOne("saehan.getTake_seq_One", paraMap);
		return chabun;
	}

	
	@Override
	public int add_withMultiFilet(String seq) {
		fot
		int n = 
		return 0;
	}
	*/

		//파일 번호로 파일 가져오기
		@Override
		public BoardFileVO getEach_view_files(String fileno) {
			return sqlsession.selectOne("saehan.getEach_view_files", fileno);
		}

		
		//파일테이블에 있는 행 삭제하기
		@Override
		public int del_attach(Map<String, String> paraMap) {
			int n = sqlsession.delete("saehan.del_attach", paraMap);
			return n;
		}

		//자유게시판 글 수정하기
		@Override
		public int freeboard_edit(BoardVO boardvo) {
			return sqlsession.update("saehan.freeboard_edit", boardvo);
		}

		
		@Override
		public int deleteFile(String fileno) {
			int n = sqlsession.delete("saehan.file_delete", fileno);
			return n;
		}

		
		//파일 삭제하면 글테이블의 filename 유무 0으로 만들기  (1은 파일 존재 , 0은 파일존재 하지 않음)
		@Override
		public int getfreeboard_filename_clear(Map<String, String> paraMap) {
			int n = sqlsession.update("saehan.freeboard_filename_clear", paraMap);
			return n;
		}

		//자유게시판 파일 추가하기
		@Override
		public int getfreeboard_filename_add(Map<String, String> paraMap) {
			int n = sqlsession.update("saehan.freeboard_filename_add", paraMap);
			return n;
		}

		//공지사항 첨부파일 추가하기
		@Override
		public int notice_insertFiles(List<NoticeboardFileVO> fileList) {
			int n = sqlsession.insert("saehan.notice_insertFiles", fileList);
			return n;
		}

		
		//공지사항 첨부파일 없는 거 글 쓰기
		@Override
		public int nofile_notice_add(NoticeboardVO boardvo) {
			int n = sqlsession.insert("saehan.notice_add_noFile", boardvo);
			return n;
		}

		
		//공지사항 첨부파일 목록 조회하기
		@Override
		public List<NoticeboardFileVO> getView_notice_files(String seq) {
			return sqlsession.selectList("saehan.getView_notice_files", seq);
		}


		@Override
		public int notice_del_attach(Map<String, String> paraMap) {
			int n = sqlsession.delete("saehan.notice_del_attach", paraMap);
			return n;
		}


		@Override
		public int notice_nofile_del(Map<String, String> paraMap) {
			int n = sqlsession.delete("saehan.notice_nofile_del", paraMap);
			return n;
		}

		//파일번호 공지사항에서 첨부파일 하나만 가져오기
		@Override
		public NoticeboardFileVO getNotice_Each_view_files(String fileno) {
			return sqlsession.selectOne("saehan.getNotice_Each_view_files", fileno);
		}
		
		
		//공지사항 글 수정하기
		@Override
		public int Noticeboard_edit(NoticeboardVO boardvo) {
			return sqlsession.update("saehan.noticeboard_edit", boardvo);
		}


		@Override
		public int notice_delete_file(Map<String, String> paraMap) {
			// TODO Auto-generated method stub
			return 0;
		}

		
		// update_attachfile ----
		@Override
		public String noticeboard_update_attachfile(String fk_seq) {
			String attachfile = sqlsession.selectOne("saehan.noticeboard_update_attachfile", fk_seq);
			return attachfile;
		}


		@Override
		public int getnoticeboard_filename_clear(Map<String, String> paraMap) {
			int n = sqlsession.update("saehan.noticeboard_filename_clear", paraMap);
			return n;
		}


		@Override
		public int getnoticeboard_filename_add(Map<String, String> paraMap) {
			int n = sqlsession.update("saehan.noticeboard_filename_add", paraMap);
			return n;
		}


		@Override
		public String freeboard_update_attachfile(String fk_seq) {
			String attachfile = sqlsession.selectOne("saehan.freeboard_update_attachfile", fk_seq);
			return attachfile;

		}
		
		

	
		
		
		


		

	




		
	

	
	
	
	
	
	


	
}
