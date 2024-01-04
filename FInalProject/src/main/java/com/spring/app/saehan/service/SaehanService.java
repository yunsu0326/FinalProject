package com.spring.app.saehan.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.CommentVO;
import com.spring.app.domain.NoticeboardVO;
import com.spring.app.domain.BoardVO;

public interface SaehanService {

	// 총 게시물 건수(totalCount) 구하기 - 검색이 있을 때와 검색이 없을때 로 나뉜다.
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함 한 것)  
	List<BoardVO> boardListSearch_withPaging(Map<String, String> paraMap);

	// 검색어 입력시 자동글 완성하기 
	List<String> wordSearchShow(Map<String, String> paraMap);
	
	//글쓰기(파일첨부가 없는 글쓰기) 
	int add(BoardVO boardvo);
	
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

	//공지사항 글쓰기(파일첨부가 있는 공지사항 글쓰기) 
	int notice_add_withFile(NoticeboardVO boardvo);
	
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


}
