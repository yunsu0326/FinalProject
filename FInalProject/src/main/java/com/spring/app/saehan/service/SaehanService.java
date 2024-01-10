package com.spring.app.saehan.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.CommentVO;
import com.spring.app.domain.NoticeboardFileVO;
import com.spring.app.domain.NoticeboardVO;
import com.spring.app.domain.BoardFileVO;
import com.spring.app.domain.BoardVO;

public interface SaehanService {

	// 총 게시물 건수(totalCount) 구하기 - 검색이 있을 때와 검색이 없을때 로 나뉜다.
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함 한 것)  
	List<BoardVO> boardListSearch_withPaging(Map<String, String> paraMap);

	// 검색어 입력시 자동글 완성하기 
	List<String> wordSearchShow(Map<String, String> paraMap);
	
	//글쓰기(파일첨부가 없는 글쓰기) 
	int add_nofile(BoardVO boardvo);
	
	//글쓰기(파일첨부가 있는 글쓰기) 
	int add_withFile(BoardVO boardvo);
	
	//글 조회수 증가는 없고 단순히 글 1개만 조회를 해주는 것
	BoardVO getView_no_increase_readCount(Map<String, String> paraMap);
	
	//글 수정하기
	int edit(BoardVO boardvo);
	
	//글 조회수 증가와 함께 글1개를 조회를 해주는 것 
	BoardVO getView(Map<String, String> paraMap);
	
	//글 삭제하기 
	int del(Map<String, String> paraMap);
	
	//원게시물에 딸린 댓글들을 페이징 처리해서 조회해오기
	int getCommentTotalPage(Map<String, String> paraMap);
	
	//원게시물에 딸린 댓글들을 페이징 처리해서 조회해오기
	List<CommentVO> getCommentList_Paging(Map<String, String> paraMap);
	
	//댓글쓰기(transaction 처리)
	int addComment(CommentVO commentvo);

	//파일첨부가 되어진 댓글 1개에서 서버에 업로드되어진 파일명과 오리지널파일명을 조회해주는 것  
	CommentVO getComment_One(Map<String, String> paraMap);
	
	//댓글 수정하기
	int getupdate_review(Map<String, String> paraMap, CommentVO commentvo);
	
	//댓글 파일이름 조회하기
	CommentVO getView_comment(Map<String, String> paraMap);
	
	//파일첨부가 되어진 댓글 1개에서 서버에 업로드되어진 파일명과 오리지널파일명을 조회해주는 것 
	CommentVO getCommentOne(String seq);
	
	//원게시물에 딸린 댓글들을 조회해오기
	List<CommentVO> getCommentList(String parentSeq);

	//첨부 파일있는 댓글 삭제하기
	int del_comment(Map<String, String> paraMap, CommentVO commentvo);

	//첨부 파일 없는 댓글 삭제하기
	int del_comment_nofile(Map<String, String> paraMap, CommentVO commentvo);

	
	////공지사항/////
	
	//공지사항의 총 게시물 수 가져오기
	int getNoticeTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 공지사항 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함 한 것) 
	List<NoticeboardVO> noticeListSearch_withPaging(Map<String, String> paraMap);

	//공지사항 글쓰기(파일첨부가 없는 공지사항 글쓰기) 
	int notice_add(NoticeboardVO boardvo);

	
	//공지사항 조회수 증가와 함께 공지사항 1개를 조회를 해주는 것 
	NoticeboardVO getNoticeView(Map<String, String> paraMap);

	//공지사항 조회수 증가 없이 공지사항 1개를 조회를 해주는 것 
	NoticeboardVO getnotice_View_no_increase_readCount(Map<String, String> paraMap);

	//공지사항 글 1개 삭제하기
	int notice_del(Map<String, String> paraMap);

	//공지사항 글 1개 수정하기
	int notice_edit(NoticeboardVO boardvo);


	//자유게시판 파일 삭제하기
	int delete_file(Map<String, String> paraMap, BoardVO boardvo);

	//자유게시판 파일첨부하기 
 	int edit_withFile(BoardVO boardvo);
 	
 	//공지사항 검색어 입력시 자동글 완성하기 
	List<String> notice_wordSearchShow(Map<String, String> paraMap);

	//공지사항 파일 삭제하기
	int notice_delete_file(Map<String, String> paraMap, NoticeboardVO boardvo);

	//공지사항에 있는 파일 수정하기
	int notice_edit_withFile(NoticeboardVO boardvo);



	
	/*
	//글번호 가져오기
	int getTake_seq(Map<String, String> paraMap);
	*/
	

	//자유게시판 다중파일첨부하기 
	//int add_withMultiFile(BoardVO boardvo);

	//boolean add_withMultiFile(BoardVO boardvo, Map<String, Object> paraMap);

	//글쓰기 완료
	boolean addEnd(Map<String, Object> paraMap);
	
	//첨부파일 전체 가져오기
	List<BoardFileVO> getView_files(String seq);

	//각각의 첨부파일 불러오기 
	BoardFileVO getEach_view_files(String fileno);

	//첨부파일 삭제하기 
	int del_attach(Map<String, String> paraMap);

	boolean freeboard_edit(Map<String, Object> paraMap);

	//자유게시판 파일 삭제하기
	boolean deleteFile(String fileno, String path);

	//파일 삭제하면 글테이블의 filename 유무 0으로 만들기  (1은 파일 존재 , 0은 파일존재 하지 않음)
	int getfreeboard_filename_clear(Map<String, String> paraMap);


	//파일 삭제하면 글테이블의 filename 유무 1로 만들기  (1은 파일 존재 , 0은 파일존재 하지 않음)
	int getfreeboard_filename_add(Map<String, String> paraMap);

	//공지사항 글쓰기(파일첨부가 있는 공지사항 글쓰기) 
	boolean notice_addEnd(Map<String, Object> paraMap);

	//공지사항 글쓰기(파일첨부가 없는 공지사항 글쓰기) 
	int nofile_notice_add(NoticeboardVO boardvo);

	//공지사항 첨부파일 목록 가져오기 
	List<NoticeboardFileVO> getView_notice_files(String seq);

	//공지사항 첨부파일 삭제하기
	int notice_del_attach(Map<String, String> paraMap);

	//공지사항 글인데 첨부파일 없는거 삭제하기
	int notice_nofile_del(Map<String, String> paraMap);

	//공지사항에서 파일 하나만 가져오기
	NoticeboardFileVO getNotice_Each_view_files(String fileno);

	//공지사항 수정하기
	boolean notice_board_edit(Map<String, Object> paraMap);

	//수정에서 각각의 첨부파일 삭제하기
	boolean notice_delete_file(String fileno, String path);
	
	// 공지사항 파일 유무 검정
	String noticeboard_update_attachfile(String fk_seq);

	//공지사항 글 파일유무를 0으로 만들기
	int getnoticeboard_filename_clear(Map<String, String> paraMap);

	//공지사항 글 파일유무를 1로 만들기
	int getnoticeboard_filename_add(Map<String, String> paraMap);

	//자유게시판 파일 유무 검정
	String freeboard_update_attachfile(String fk_seq);

	// boolean deleteFile(String fileno, String path, Map<String, String> paraMap);

	

	




}
