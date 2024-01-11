package com.spring.app.digitalmail.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.digitalmail.model.DigitalmailDAO;
import com.spring.app.digitalmail.domain.EmailStopVO;
import com.spring.app.digitalmail.domain.EmailVO;

//==== Service 선언 ====
//트랜잭션 처리를 담당하는 곳, 업무를 처리하는 곳, 비지니스(Business)단 
//@Component
@Service
public class DigitalmailService_imple implements DigitalmailService {
 	
 	@Autowired  
	private DigitalmailDAO dao;
	
	// 내가 받은 총 메일 갯수
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int cnt = dao.getTotalCount(paraMap);
		return cnt;
	}
	// 전자메일 시작
	@Override
	public ModelAndView digitalmail(ModelAndView mav, Map<String, String> paraMap) {
		List<EmailVO> emailVOList = null;
		emailVOList = dao.SelectMyEmail_withPaging(paraMap);
		// System.out.println("이거 떠야되는데 =>"+emailVOList.size());
		mav.addObject("emailVOList", emailVOList);
		mav.setViewName("digitalmail/digitalmail.tiles_digitalmail");	
		return mav;
	}
	
	// 이메일 키워드 입력시 자동글 완성하기 //
	@Override
	public List<String> emailWordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = dao.emailWordSearchShow(paraMap);
		return wordList;
	}
	
	// === 이메일 쓰기 페이지 이동  === //
	@Override
	public ModelAndView digitalmailwrite(ModelAndView mav) {
		List<String> EmailList = dao.getEmailList();
		mav.addObject("EmailList", EmailList);
		mav.setViewName("digitalmailwrite/digitalmailwrite.tiles_digitalmail");
		return mav;
	}
	
	// 파일 업로드 완료 후 메일 쓰기
	@Override
	public int emailsucadd(Map<String, Object> paraMap) {
		
		String email_seq = dao.getEmailseq();
		
		// System.out.println(email_seq);
		
		paraMap.put("email_seq", email_seq);
		
		System.out.println("paraMap.get(\"send_emailstop_seq\")=>"+paraMap.get("send_emailstop_seq")); 
		
		int n = dao.emailsucadd(paraMap);
		
		int lastsuc=0;
		int stopemaildel = 0;
		System.out.println("이름=>"+paraMap.get("email_seq"));
		System.out.println("중요도=>"+paraMap.get("impt"));
		System.out.println("이메일타입=>"+paraMap.get("emailType"));
		
		if(n == 1) {
			lastsuc = dao.emailspilt(paraMap);
		}
		if (lastsuc == 1 & paraMap.get("send_emailstop_seq") != null) {
			stopemaildel = dao.stopemaildel(paraMap);
		}
		
		return lastsuc;
	}
	
	// 임시저장
	@Override
	public int emailaddstop(Map<String, Object> paraMap) {
		
		String send_emailstop_seq = dao.getEmailStopseq();
		System.out.println("send_emailstop_seq=>"+send_emailstop_seq);
		paraMap.put("send_emailstop_seq", send_emailstop_seq);
		int addStop = dao.emailaddStop(paraMap);
		
		return addStop;
	}
	
	// 이메일 한개 보는 페이지
	@Override
	public ModelAndView digitalmailview(ModelAndView mav, Map<String, String> paraMap) {
		EmailVO emailVO = dao.SelectEmail(paraMap);
		System.out.println("이거 떠야되는데 =>"+emailVO.getEmail_subject());
		EmailVO emailVO2 = dao.getseqfav(paraMap);
		mav.addObject("emailVO", emailVO);
		mav.addObject("emailVO2", emailVO2);
		mav.setViewName("digitalmailview/digitalmailview.tiles_digitalmail");
		return mav;
	}
	
	// 비밀번호가 있을경우 알아오기
	@Override
	public String getEmailPwd(String send_email_seq) {
		String pwd = dao.getEmailPwd(send_email_seq);
		return pwd;
	}
	
	// 이메일 번호 체번
	@Override
	public EmailVO SelectEmail(Map<String, String> paraMap) {
		EmailVO emailVO = dao.SelectEmail(paraMap);
		return emailVO;
	}
	
	// 임시저장된 리스트 갯수 채번
	@Override
	public int addstopCount(Map<String, String> paraMap) {
		int cnt = dao.addstopCount(paraMap);
		return cnt;
	}
	
	// 임시저장된 이메일
	@Override
	public ModelAndView addstopView(ModelAndView mav, Map<String, String> paraMap) {
		List<EmailStopVO> emailstopList = null;
		emailstopList = dao.SelectMyStopEmail_withPaging(paraMap);
		// System.out.println("이거 떠야되는데 =>"+emailVOList.size());
		mav.addObject("emailstopList", emailstopList);
		mav.setViewName("digitalmailstop/digitalmailstop.tiles_digitalmail");	
		return mav;
	}
	
	// 이메일  임시메일 쓰기
	@Override
	public ModelAndView digitalmailstopwrite(ModelAndView mav, Map<String, String> paraMap) {
		EmailStopVO emailstopVo = dao.SelectstopEmail(paraMap);
		
		List<String> referenceEmailList  = null;
		List<String> hidden_referenceEmailList = null;
		
		List<String> EmailList = dao.getEmailList();
		
		String recipient = emailstopVo.getFk_recipient_email();
		String reference = emailstopVo.getFk_reference_email();
		String hidden_reference = emailstopVo.getFk_hidden_reference_email();
		
		System.out.println("recipient =>"+recipient);
		
		List<String> recipientEmailList = dao.getrecipientEmailList(recipient);
		mav.addObject("recipientEmailList", recipientEmailList);
		
		// System.out.println("recipientEmailList=>" + recipientEmailList.get(0));
		
		System.out.println("reference=>"+reference);
		
		
		if(reference != null){
			referenceEmailList = dao.getreferenceEmailList(reference);
			mav.addObject("referenceEmailList", referenceEmailList);
		}
		else {
			mav.addObject("referenceEmailList", "null");	
		}
		System.out.println("referenceEmailList=>"+referenceEmailList);
				
		if(hidden_reference != null){
			hidden_referenceEmailList = dao.gethidden_referenceEmailList(hidden_reference);
			mav.addObject("hidden_referenceEmailList", hidden_referenceEmailList);
		}
		else {
			mav.addObject("hidden_referenceEmailList", "null");
		}
		System.out.println("hidden_referenceEmailList=>"+hidden_referenceEmailList);
		// System.out.println("hidden_reference=>"+hidden_reference);

		mav.addObject("EmailList", EmailList);
		
		//mav.addObject("referenceEmailList", referenceEmailList);
		//mav.addObject("hidden_referenceEmailList", hidden_referenceEmailList);
		// System.out.println("이거 떠야되는데 =>"+emailstopVo.getEmail_subject());
		mav.addObject("emailstopVo", emailstopVo);
		mav.setViewName("digitalmailstopwrite/digitalmailstopwrite.tiles_digitalmail");
		return mav;
	}
	
	@Override
	public int emailaddupdate(Map<String, Object> paraMap) {
		int addupdate = dao.emailaddupdate(paraMap);
		return addupdate;
	}
	
	// receipt_favorites update하기
		@Override
		public int receipt_favorites_update(Map<String, String> paraMap) {
			int n = dao.receipt_favorites_update(paraMap);
			return n;
		}

		// receipt_favorites 값 가져오기
		@Override
		public String select_receipt_favorites(String receipt_mail_seq) {
			String receipt_favorites = dao.select_receipt_favorites(receipt_mail_seq);
			return receipt_favorites;
		}
		
		// email_receipt_read_count update 하기
		@Override
		public int email_receipt_read_count_update(String receipt_mail_seq) {
			int n = dao.email_receipt_read_count_update(receipt_mail_seq);
			return n;
		}

		// email_receipt_read_count 값 가져오기
		@Override
		public String select_email_receipt_read_count(String receipt_mail_seq) {
			String email_receipt_read_count = dao.select_email_receipt_read_count(receipt_mail_seq);
			return email_receipt_read_count;
		}

		// receipt_important 값 가져오기
		@Override
		public String select_receipt_important(String receipt_mail_seq) {
			String receipt_important = dao.select_receipt_important(receipt_mail_seq);
			return receipt_important;
		}

		// receipt_important update 하기
		@Override
		public int receipt_important_update(Map<String, String> paraMap) {
			int n = dao.receipt_important_update(paraMap);
			return n;
		}
		
		// 답장하기 이메일 가져오기
		@Override
		public Map<String, String> getsenderEmail(String sender , String send_email_seq) {
			
			Map<String, String> paraMap = new HashMap<>();
			String senderEmail = dao.getsenderEmail(sender);
			EmailVO emailvo = dao.getSubjectandcontent(send_email_seq);
			
			paraMap.put("senderEmail",senderEmail);
			paraMap.put("email_content",emailvo.getEmail_contents());
			paraMap.put("email_subject",emailvo.getEmail_subject());
			
			System.out.println("email_content =>"+emailvo.getEmail_contents());
			System.out.println("email_subject =>"+emailvo.getEmail_subject());
			return paraMap;
		}
		@Override
		public int email_del(Map<String, Object> receipt_mailMap) {
			int del = dao.email_del(receipt_mailMap);
			return del;
		}
		// 이메일 읽음 안읽음 처리
		@Override
		public int total_email_receipt_read_count_update(Map<String, Object> receipt_mailMap) {
			int readcnt = dao.total_email_receipt_read_count_update(receipt_mailMap);
			return readcnt;
		}
		//
		@Override
		public int onedel(String receipt_mail_seq) {
			int n = dao.onedel(receipt_mail_seq);
			return n;
		}
		@Override
		public int emailstop_del(Map<String, Object> receipt_mailMap) {
			int del = dao.emailstop_del(receipt_mailMap);
			return del;
		}
	
}
