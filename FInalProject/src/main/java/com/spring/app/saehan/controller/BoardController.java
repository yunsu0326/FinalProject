package com.spring.app.saehan.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.spring.app.domain.CommentVO;
import com.spring.app.common.FileManager;
import com.spring.app.common.MyUtil;
import com.spring.app.domain.BoardVO;
import com.spring.app.saehan.service.SaehanService;
import com.spring.app.domain.EmployeesVO;
import com.spring.app.domain.NoticeboardVO;

@Controller
public class BoardController {

	@Autowired
	private SaehanService service;

	@Autowired
	private FileManager fileManager;

	// ========== 글 리스트 페이지 만들기 시작 =============
	@GetMapping("/freeboard.gw")
	public ModelAndView mainboard(ModelAndView mav, HttpServletRequest request) {

		List<BoardVO> boardList = null;

		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");

		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");

		if (searchType == null) {
			searchType = "";
		}

		if (searchWord == null) {
			searchWord = "";
		}

		if (searchWord != null) {
			searchWord = searchWord.trim();
		}

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);

		int totalCount = 0; // 총 게시물 건수
		int sizePerPage = 10; // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo = 0; // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0; // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)

		// 총 게시물 건수(totalCount) 가져오기
		totalCount = service.getTotalCount(paraMap);

		totalPage = (int) Math.ceil((double) totalCount / sizePerPage);

		if (str_currentShowPageNo == null) {
			currentShowPageNo = 1;
		}

		else {
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				if (currentShowPageNo < 1 || currentShowPageNo > totalPage) {
					currentShowPageNo = 1;
				}
			} catch (NumberFormatException e) {
				currentShowPageNo = 1;
			}
		} // end of else{------------------------

		int startRno = ((currentShowPageNo - 1) * sizePerPage) + 1; // 시작 행번호
		int endRno = startRno + sizePerPage - 1; // 끝 행번호

		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));

		boardList = service.boardListSearch_withPaging(paraMap);
		// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함 한 것)

		mav.addObject("boardList", boardList);

		if ("subject".equals(searchType) || "content".equals(searchType) || "subject_content".equals(searchType)
				|| "name".equals(searchType)) {

			mav.addObject("paraMap", paraMap);

		}

		// ===== 페이지바 만들기 ===== //
		int blockSize = 10;
		int loop = 1;
		int pageNo = ((currentShowPageNo - 1) / blockSize) * blockSize + 1;

		String pageBar = "<ul style='list-style:none;'>";
		String url = "freeboard.gw";

		// === [맨처음][이전] 만들기 === //
		if (pageNo != 1) {
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt; color:black;'><a href='" + url + "?searchType="
					+ searchType + "&searchWord=" + searchWord + "&currentShowPageNo=1'>[맨처음]</a></li>";
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt; color:black;'><a href='" + url + "?searchType="
					+ searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + (pageNo - 1)
					+ "'>[이전]</a></li>";
		}

		while (!(loop > blockSize || pageNo > totalPage)) {

			if (pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:black; padding:2px 4px;'>"
						+ pageNo + "</li>";
			}

			else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; color:black;'><a href='" + url
						+ "?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + pageNo
						+ "'>" + pageNo + "</a></li>";
			}

			loop++;
			pageNo++;

		} // end of while-------------------------

		// ===== [다음][마지막] 만들기 ===== //
		if (pageNo <= totalPage) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt; color:black;'><a href='" + url + "?searchType="
					+ searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + pageNo + "'>[다음]</a></li>";
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt; color:black;'><a href='" + url + "?searchType="
					+ searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + totalPage + "'>[마지막]</a></li>";
		}

		pageBar += "</ul>";

		mav.addObject("pageBar", pageBar);

		// === 특정 글제목을 클릭한 뒤 상세내용을 본 이후 사용자가 "검색된결과목록보기" 버튼을 클릭했을때 현재 페이지 주소를 뷰단으로 넘겨준다.
		String goBackURL = MyUtil.getCurrentURL(request);

		mav.addObject("goBackURL", goBackURL);
		mav.setViewName("mshboard/freeboard.tiles_MTS");

		return mav;

	} // end of public ModelAndView mainboard(ModelAndView mav,HttpServletRequest request) {-----------

	// ========== 글 리스트 페이지 만들기 끝 =============

	// ===== 검색어 입력시 자동글 완성하기 시작 ===== //
	@ResponseBody
	@GetMapping(value = "/wordSearchShow.gw", produces = "text/plain;charset=UTF-8")
	public String wordSearchShow(HttpServletRequest request) {

		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);

		List<String> wordList = service.wordSearchShow(paraMap);

		JSONArray jsonArr = new JSONArray(); // []

		if (wordList != null) {
			for (String word : wordList) {
				JSONObject jsonObj = new JSONObject(); // {}
				jsonObj.put("word", word);
				jsonArr.put(jsonObj); // [{},{},{}]
			} // end of for------------
		}

		return jsonArr.toString();
	}
	// ===== 검색어 입력시 자동글 완성하기 끝 ===== //

	// ===== 글쓰기 페이지 요청 시작 ===== ///
	@GetMapping("/add.gw")
	public ModelAndView requiredLogin_add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {

		// === 답변글쓰기가 추가된 경우 시작 === //
		String subject = "[답변] " + request.getParameter("subject");
		String groupno = request.getParameter("groupno");
		String fk_seq = request.getParameter("fk_seq");
		String depthno = request.getParameter("depthno");
		
		
		if (fk_seq == null) {
			fk_seq = "";
		}

		mav.addObject("subject", subject);
		mav.addObject("groupno", groupno);
		mav.addObject("fk_seq", fk_seq);
		mav.addObject("depthno", depthno);
		// === 답변글쓰기가 추가된 경우 끝 === //

		mav.setViewName("mshboard/add.tiles_MTS");

		return mav;
	}// end of public ModelAndView requiredLogin_add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) { ------------
		// ===== 글쓰기 페이지 요청 끝 ===== //

//	// ====== 게시판 글쓰기 완료 요청 ======= //
//	@ResponseBody
//	@PostMapping("/addEnd.gw")
//	public String addEnd(Map<String, String> paraMap, BoardVO boardvo, MultipartHttpServletRequest mrequest) throws Exception {
//		// ===== 첨부파일이 있는 경우 작업 시작 !!! ======
//		String recipient = mrequest.getParameter("fk_email");
//		String subject = mrequest.getParameter("subject");
//		String content = mrequest.getParameter("content");
//		
//		List<MultipartFile> fileList = mrequest.getFiles("file_arr"); 
//		
//		System.out.println(fileList);
//		String path ="";
//		String[] arr_attachFilename = null;
//		if (fileList != null) {
//			// attach(첨부파일)가 비어 있지 않으면(즉, 첨부파일이 있는 경우라면)
//			HttpSession session = mrequest.getSession();
//
//			String root = session.getServletContext().getRealPath("/");
//
//			path = root + "resources" + File.separator + "Filename";
//			String newFileName = "";
//			
//			File dir = new File(path);
//			       if(!dir.exists()) {
//			          dir.mkdirs();
//			       }
//			
//		   
//		      if(fileList != null && fileList.size() > 0) {
//		         arr_attachFilename = new String[fileList.size()];
//		            
//		         for(int i=0; i<fileList.size(); i++) {
//		            MultipartFile mtfile = fileList.get(i);
//		            System.out.println("파일명 : " + mtfile.getOriginalFilename() + " / 파일 크기 : " + mtfile.getSize());
//		             
//		            try {
//			               /*
//			               File 클래스는 java.io 패키지에 포함되며, 입출력에 필요한 파일이나 디렉터리를 제어하는 데 사용된다.
//			                            파일과 디렉터리의 접근 권한, 생성된 시간, 경로 등의 정보를 얻을 수 있는 메소드가 있으며, 
//			                            새로운 파일 및 디렉터리 생성, 삭제 등 다양한 조작 메서드를 가지고 있다.
//			               */
//			               // === MultipartFile 을 File 로 변환하여 탐색기 저장폴더에 저장하기 시작 ===
//			               File attachFile = new File(path+File.separator+mtfile.getOriginalFilename());
//			               
//			               String originalFilename = ((MultipartFile) fileList).getOriginalFilename();
//			               
//						   newFileName = fileManager.doFileUpload(bytes, originalFilename, path);
//			               
//						   mtfile.transferTo(attachFile);
//			               /*
//			                  form 태그로 부터 전송받은 MultipartFile mtfile 파일을 지정된 대상 파일(attachFile)로 전송한다.
//			                                              만약에 대상 파일(attachFile)이 이미 존재하는 경우 먼저 삭제된다.
//			               */
//			               // 탐색기에서 C:\NCS\workspace_spring_framework\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\email_attach_file 에 가보면
//			               // 첨부한 파일이 생성되어져 있음을 확인할 수 있다.
//			               // === MultipartFile 을 File 로 변환하여 탐색기 저장폴더에 저장하기 끝 ===   
//			                 
//			               arr_attachFilename[i] = mtfile.getOriginalFilename(); // 첨부파일명들을 기록한다. 
//		   
//		            } catch(Exception e) {
//		               e.printStackTrace();
//		            } 
//		         }
//		      }
//		      
//		      int n = 0;
//
//				if (fileList.isEmpty()) {
//					// 파일첨부가 없는 경우라면
//					n = service.add(boardvo);
//				} 
//				
//				else {
//					// 파일첨부가 있는 경우라면
//					n = service.add_withFile(boardvo);
//				}
//				
//				if (n == 1) {
//					return("redirect:/noticeboard.gw");
//				} else {
//					return("redirect:/index.gw");
//				}
//		     
//		       
//		// ===== !!! 첨부파일이 있는 경우 작업 끝 !!! =====
//		}
//
//		
//		  JSONObject jsonObj = new JSONObject();
//	      
//	      String[] arr_recipient = recipient.split("\\;");
//	      
//	      for(String recipient_email : arr_recipient) {
//	         Map<String, Object> paraMap1 = new HashMap<>();
//	         paraMap1.put("recipient", recipient_email);
//	         paraMap1.put("subject", subject);
//	         paraMap1.put("content", content);
//	         
//	         if(fileList != null && fileList.size() > 0) {
//	            paraMap1.put("path", path); // path 는 첨부파일들이 저장된 WAS(톰캣)의 폴더의 경로명이다.
//	            paraMap1.put("arr_attachFilename", arr_attachFilename); // arr_attachFilename 은 첨부파일명들이 저장된 배열이다. 
//	         }   
//	      
//	         
//	      }//end of for --------------------------     
//	     
//	     // System.out.println(jsonObj.toString()); ////"{"result":1}"
//	      
//	     return jsonObj.toString();
//
//		
//	} // end of public ModelAndView addEnd(Map<String, String> paraMap, ModelAndView mav, BoardVO boardvo, MultipartHttpServletRequest mrequest) { ----------
//	// ====== 게시판 글쓰기 완료 요청 끝======= //
//
//	
	
	// ====== 스마트에디터. 드래그앤드롭을 사용한 다중사진 파일 업로드 시작 ====== //
	@PostMapping(value = "/image/multiplePhotoUpload.gw")
	public void multiplePhotoUpload(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		String root = session.getServletContext().getRealPath("/");
		String path = root + "resources" + File.separator + "photo_upload";

		File dir = new File(path);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {
			String filename = request.getHeader("file-name");

			InputStream is = request.getInputStream(); // is는 네이버 스마트 에디터를 사용하여 사진첨부하기 된 이미지 파일임.

			String newFilename = fileManager.doFileUpload(is, filename, path);

			int width = fileManager.getImageWidth(path + File.separator + newFilename);

			if (width > 600) {
				width = 600;
			}

			// 이미지 불러오는 경우
			String ctxPath = request.getContextPath();

			String strURL = "";
			strURL += "&bNewLine=true&sFileName=" + newFilename;
			strURL += "&sWidth=" + width;
			strURL += "&sFileURL=" + ctxPath + "/resources/photo_upload/" + newFilename;

			// === 웹브라우저 상에 사진 이미지를 쓰기 === //
			PrintWriter out = response.getWriter();
			out.print(strURL);

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		

	}// end of public void multiplePhotoUpload(HttpServletRequest request, HttpServletResponse response) { -------------------
		// ====== 스마트에디터. 드래그앤드롭을 사용한 다중사진 파일 업로드 끝====== //

	// ===== 글을 수정하는 페이지 요청 시작 ===== //
	@GetMapping("/edit.gw")
	public ModelAndView requiredLogin_edit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		// 글 수정해야 할 글번호 가져오기
		String seq = request.getParameter("seq");
		String message = "";
		try {
			Integer.parseInt(seq);
			// 글 수정해야할 글1개 내용가져오기
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("seq", seq);
			BoardVO boardvo = service.getView_no_increase_readCount(paraMap);
			// 글조회수(readCount) 증가 없이 단순히 글 1개만 조회해주는 것이다.

			if (boardvo == null) {
				message = "글 수정이 불가합니다";
			}

			else {
				HttpSession session = request.getSession();
				EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");

				if (!loginuser.getEmail().equals(boardvo.getFk_email())) {
					message = "다른 사용자의 글은 수정이 불가합니다";
				}

				else {
					// 자신의 글을 수정할 경우
					mav.addObject("boardvo", boardvo);
					mav.setViewName("mshboard/edit.tiles_MTS");

					return mav;
				}
			}
		} catch (NumberFormatException e) {
			message = "글 수정이 불가합니다";
		}
		String loc = "javascript:history.back()";

		mav.addObject("message", message);
		mav.addObject("loc", loc);
		mav.setViewName("msg");

		return mav;
	}// end of public ModelAndView requiredLogin_edit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {------
		//// ===== 글을 수정하는 페이지 요청 끝 ===== //

	// ===== 글을 수정하는 페이지 완료하기 시작===== //
	@PostMapping("/editEnd.gw")
	public ModelAndView editEnd(Map<String, String> paraMap, ModelAndView mav, BoardVO boardvo, HttpServletRequest request,
	MultipartHttpServletRequest mrequest) {		
		MultipartFile attach = boardvo.getAttach();

		 
		if (attach != null && !attach.isEmpty()) {
			// attach(첨부파일)가 비어 있지 않으면(즉, 첨부파일이 있는 경우라면)
			HttpSession session = mrequest.getSession();

			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";

			byte[] bytes = null;
			long FileSize = 0;

			try {
				bytes = attach.getBytes();

				String originalFilename = attach.getOriginalFilename();
				String newFileName = fileManager.doFileUpload(bytes, originalFilename, path);
				boardvo.setFileName(newFileName);
				boardvo.setOrgFilename(originalFilename);

				FileSize = attach.getSize(); // 첨부파일의 크기(단위는 byte임)
				boardvo.setFileSize(String.valueOf(FileSize));

			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ===== !!! 첨부파일이 있는 경우 작업 끝 !!!
		
		int n = 0;
		//System.out.println("확인 : "+attach);
		if (attach == null || attach.isEmpty() ) {
			n = service.edit(boardvo);
		} 
		
		if(attach != null && !attach.isEmpty() ){
			// 파일첨부가 있는 경우라면
			n = service.edit_withFile(boardvo);
		}


		if (n == 1) {
			mav.addObject("message", "글 수정 성공!!");
			mav.addObject("loc", request.getContextPath() + "/view.gw?seq=" + boardvo.getSeq());
			mav.setViewName("msg");
		}
		
		return mav;
	}// end of public ModelAndView editEnd(ModelAndView mav, BoardVO boardvo, HttpServletRequest request) {--------
	// ===== 글을 수정하는 페이지 완료하기 끝===== //
	
	
	// ========== 글을 수정하는 페이지에서 첨부파일 삭제 시작 =============//
	@ResponseBody
	@GetMapping(value = "/delete_file.gw", produces = "text/plain;charset=UTF-8")
	public String deletefile(ModelAndView mav, HttpServletRequest request) {
		String seq = request.getParameter("seq");

		Map<String, String> paraMap = new HashMap<>();

		paraMap.put("seq", seq);

		int n;
		BoardVO boardvo = service.getView_no_increase_readCount(paraMap);

		String fileName = boardvo.getFileName();

		if (fileName != null && !"".equals(fileName)) {
			HttpSession session = request.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";
			paraMap.put("path", path); // 삭제해야할 파일이 저장된 경로
			paraMap.put("fileName", fileName); // 삭제해야할 파일명
		}

		n = service.delete_file(paraMap, boardvo);

		boolean success = false;

		if (n == 1) {
			mav.addObject("message", "파일 삭제 성공!!");
			mav.addObject("loc", request.getContextPath() + "/freeboard.gw");
			mav.setViewName("msg");
			success = true;
		} else {
			success = false;
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("success", success);
		String json = jsonObj.toString();
		System.out.println(json);

		return jsonObj.toString();
	}// end of public String deletefile(ModelAndView mav, HttpServletRequest request) { ---------
	
	// ========== 글을 수정하는 페이지에서 첨부파일 삭제 끝 =============//
	
	
	// ===== 글을 삭제하는 페이지 시작===== //
	@GetMapping("/del.gw")
	public ModelAndView requiredLogin_del(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {

		// 삭제해야 할 글번호 가져오기
		String seq = request.getParameter("seq");
		String message = "";

		try {
			Integer.parseInt(seq);
			// 삭제해야할 글1개 내용가져오기

			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("seq", seq);

			BoardVO boardvo = service.getView_no_increase_readCount(paraMap);
			// 글조회수(readCount) 증가 없이 단순히 글 1개만 조회해주는 것이다.

			if (boardvo == null) {
				message = "글 삭제가 불가합니다";
			} else {
				HttpSession session = request.getSession();
				EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");

				mav.addObject("boardvo", boardvo);
				mav.setViewName("mshboard/del.tiles_MTS");

				return mav;
			} // end of try {----------------
		} catch (NumberFormatException e) {
			message = "글 삭제가 불가합니다";
		} // end of else{-------------

		String loc = "javascript:history.back()";
		mav.addObject("message", message);
		mav.addObject("loc", loc);

		mav.setViewName("msg");

		return mav;
	}// end of public ModelAndView requiredLogin_del(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {---
		// ===== 글을 삭제하는 페이지 요청하기 끝 ===== //

	// ====== 글을 삭제하는 페이지 완료하기 시작====== //
	@PostMapping("/delEnd.gw")
	public ModelAndView delEnd(ModelAndView mav, HttpServletRequest request) {

		String seq = request.getParameter("seq");

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("seq", seq);

		// === 파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 시작 === //
		paraMap.put("searchType", "");
		paraMap.put("searchWord", "");

		BoardVO boardvo = service.getView(paraMap);
		String fileName = boardvo.getFileName();

		if (fileName != null && !"".equals(fileName)) {

			HttpSession session = request.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";

			paraMap.put("path", path); // 삭제해야할 파일이 저장된 경로
			paraMap.put("fileName", fileName); // 삭제해야할 파일명
		}
		// === 파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 끝 === //

		int n = service.del(paraMap);

		if (n == 1) {
			mav.addObject("message", "글 삭제 성공!!");
			mav.addObject("loc", request.getContextPath() + "/freeboard.gw");
			mav.setViewName("msg");
		}

		return mav;
	}// end of public ModelAndView delEnd(ModelAndView mav, HttpServletRequest request) {------
	// ====== 글을 삭제하는 페이지 완료하기 끝===== //

	// ====== 글1개를 보여주는 페이지 요청하기 시작 ====== //
	@RequestMapping("/view.gw") // ==== 특정글을 조회한 후 "검색된결과목록보기" 버튼을 클릭했을 때 돌아갈 페이지를 만들기 위함.
	public ModelAndView view(ModelAndView mav, HttpServletRequest request) {

		String seq = "";
		String goBackURL = "";
		String searchType = "";
		String searchWord = "";

		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		// redirect 되어서 넘어온 데이터가 있는지 꺼내어 와본다.

		if (inputFlashMap != null) {

			@SuppressWarnings("unchecked") // 경고 표시를 하지 말라는 뜻이다.
			Map<String, String> redirect_map = (Map<String, String>) inputFlashMap.get("redirect_map");
			seq = redirect_map.get("seq");
			searchType = redirect_map.get("searchType");

			try {
				searchWord = URLDecoder.decode(redirect_map.get("searchWord"), "UTF-8"); // 한글데이터가 포함되어 있으면 반드시 한글로
																							// 복구주어야 한다.
				goBackURL = URLDecoder.decode(redirect_map.get("goBackURL"), "UTF-8"); // 한글데이터가 포함되어 있으면 반드시 한글로 복구주어야
																						// 한다.
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (goBackURL != null && goBackURL.contains(" ")) {
				goBackURL = goBackURL.replaceAll(" ", "&");
			}
		}

		else {
			seq = request.getParameter("seq");
			goBackURL = request.getParameter("goBackURL");

			if (goBackURL != null && goBackURL.contains(" ")) {
				goBackURL = goBackURL.replaceAll(" ", "&");
			}

			// === 검색으로 조회된 글을 볼 때 view.jsp 에서 이전글제목 및 다음글제목 클릭시 사용하기 위한 것임. 시작 ===
			searchType = request.getParameter("searchType");
			searchWord = request.getParameter("searchWord");

			if (searchType == null) {
				searchType = "";
			}

			if (searchWord == null) {
				searchWord = "";
			}
			// === 검색으로 조회된 글을 볼 때 view.jsp 에서 이전글제목 및 다음글제목 클릭시 사용하기 위한 것임. 끝 ===
		}

		mav.addObject("goBackURL", goBackURL);

		try {
			Integer.parseInt(seq);
			HttpSession session = request.getSession();
			EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
			String login_email = null;

			if (loginuser != null) {
				login_email = loginuser.getEmail();
			}

			Map<String, String> paraMap = new HashMap<>();

			paraMap.put("seq", seq);
			paraMap.put("login_email", login_email);

			paraMap.put("searchType", searchType);
			paraMap.put("searchWord", searchWord);

			BoardVO boardvo = null;

			if ("yes".equals((String) session.getAttribute("readCountPermission"))) {
				// 글목록보기인 /freeboard.gw 페이지를 클릭한 다음에 특정글을 조회해온 경우이다.

				boardvo = service.getView(paraMap);
				// 글조회수 증가와 함께 글1개를 조회를 해주는 것

				session.removeAttribute("readCountPermission");
			}

			else {
				// 글목록에서 특정 글제목을 클릭하여 본 상태에서 웹브라우저에서 새로고침(F5)을 클릭한 경우이다.
				boardvo = service.getView_no_increase_readCount(paraMap);
				// 글조회수 증가는 없고 단순히 글1개만 조회를 해주는 것
			}

			mav.addObject("boardvo", boardvo);
			mav.addObject("paraMap", paraMap);

		} catch (NumberFormatException e) {
			// 이전글제목 또는 다음글제목을 클릭하여 본 상태에서
			// 웹브라우저에서 새로고침(F5)을 클릭한 경우이다.
			mav.setViewName("redirect:/freeboard.gw");
			return mav;
		}

		mav.setViewName("mshboard/view.tiles_MTS");
		return mav;
	}// end of public ModelAndView view(ModelAndView mav, HttpServletRequest request) {-------------

	// ====== 글1개를 보여주는 페이지 요청하기 끝 ====== //

	// ====== 글1개를 보여주는 페이지 요청하기 시작 ====== //
	@PostMapping("/view_2.gw")
	public ModelAndView view_2(ModelAndView mav, HttpServletRequest request, RedirectAttributes redirectAttr) {

		String seq = request.getParameter("seq");

		String goBackURL = request.getParameter("goBackURL");
		goBackURL = goBackURL.replaceAll("&", " ");

		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");

		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");

		try {
			searchWord = URLEncoder.encode(searchWord, "UTF-8");
			goBackURL = URLEncoder.encode(goBackURL, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// ==== GET 방식이 아닌 POST 방식처럼 데이터를 넘기려면 RedirectAttributes 를 사용하면 된다. 시작 ==== //
		Map<String, String> redirect_map = new HashMap<>();
		redirect_map.put("seq", seq);
		redirect_map.put("goBackURL", goBackURL);
		redirect_map.put("searchType", searchType);
		redirect_map.put("searchWord", searchWord);

		redirectAttr.addFlashAttribute("redirect_map", redirect_map);

		mav.setViewName("redirect:/view.gw");
		// ==== GET 방식이 아닌 POST 방식처럼 데이터를 넘기려면 RedirectAttributes 를 사용하면 된다. 끝 ==== //

		return mav;

	}// end of public ModelAndView view_2(ModelAndView mav, HttpServletRequest request, RedirectAttributes redirectAttr) {-----
		// ====== 글1개를 보여주는 페이지 요청하기 끝 ====== //
	
	// ====== 첨부파일 다운로드 받기 시작 ======= //
	@GetMapping(value = "/download.gw")
	public void requiredLogin_download(HttpServletRequest request, HttpServletResponse response) {

		String seq = request.getParameter("seq");
		// 첨부파일이 있는 글번호

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("seq", seq);
		paraMap.put("searchType", "");
		paraMap.put("searchWord", "");

		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = null;

		try {
			Integer.parseInt(seq);
			BoardVO boardvo = service.getView_no_increase_readCount(paraMap);

			if (boardvo == null || (boardvo != null && boardvo.getFileName() == null)) {
				out = response.getWriter();
				out.println(
						"<script type='text/javascript'>alert('존재하지 않는 글번호 이거나 첨부파일이 없으므로 파일다운로드가 불가합니다.'); history.back();</script>");
				return;
			} else { // 정상적으로 다운로드를 할 경우
				String fileName = boardvo.getFileName();
				String orgFilename = boardvo.getOrgFilename();
				HttpSession session = request.getSession();
				String root = session.getServletContext().getRealPath("/");
				String path = root + "resources" + File.separator + "files";

				// ***** file 다운로드 하기 ***** //
				boolean flag = false; // file 다운로드 성공, 실패인지 여부를 알려주는 용도
				flag = fileManager.doFileDownload(fileName, orgFilename, path, response);
				// file 다운로드 성공시 flag 는 true, 실패시 flag 는 false 를 가진다.
				if (!flag) {
					// 다운로드가 실패한 경우 메시지를 띄워준다.
					out = response.getWriter();
					out.println("<script type='text/javascript'>alert('파일다운로드가 실패되었습니다.'); history.back();</script>");
				}
			}

		} catch (NumberFormatException | IOException e) {
			try {
				out = response.getWriter();
				out.println("<script type='text/javascript'>alert('파일다운로드가 불가합니다.'); history.back();</script>");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}

	}// end of public void requiredLogin_download(HttpServletRequest request, HttpServletResponse response) {--------------
		// ====== 첨부파일 다운로드 받기 끝 ======= //

	// ====== 원게시물에 딸린 댓글들을 조회해오기(Ajax 로 처리) 시작 ====== //
	@ResponseBody
	@GetMapping(value = "/readComment.gw", produces = "text/plain;charset=UTF-8")
	public String readComment(HttpServletRequest request) {

		String parentSeq = request.getParameter("parentSeq");
		List<CommentVO> commentList = service.getCommentList(parentSeq);

		JSONArray jsonArr = new JSONArray();

		if (commentList != null) {
			for (CommentVO cmtvo : commentList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("name", cmtvo.getName());
				jsonObj.put("content", cmtvo.getContent());
				jsonObj.put("regdate", cmtvo.getRegDate());

				jsonArr.put(jsonObj);
			} // end of for------------
		} // end of if-------------

		return jsonArr.toString();
	}// public String readComment(HttpServletRequest request) {-------------------
		// ====== 원게시물에 딸린 댓글들을 조회해오기(Ajax 로 처리) 끝 ====== //

	// ====== 댓글쓰기(Ajax 로 처리) 시작 =======//
	@ResponseBody
	@PostMapping(value = "/addComment.gw", produces = "text/plain;charset=UTF-8")
	public String addComment(CommentVO commentvo) {
		// 댓글쓰기에 첨부파일이 없는 경우
		int n = 0;
		try {
			n = service.addComment(commentvo);
			// 댓글쓰기(insert) 및 원게시물(tbl_board 테이블)에 댓글의 개수 증가(update 1씩 증가)하기
		} catch (Throwable e) {
			e.printStackTrace();
		}

		JSONObject jsonObj = new JSONObject(); // {}
		jsonObj.put("n", n);
		jsonObj.put("name", commentvo.getName());

		return jsonObj.toString();
	} // end of public String addComment(CommentVO commentvo) {----------
		// ====== 댓글쓰기(Ajax 로 처리) 끝=======//

	// ====== 댓글쓰기에 첨부파일이 있는 경우 시작======//
	@ResponseBody
	@PostMapping(value = "/addComment_withAttach.gw", produces = "text/plain;charset=UTF-8")
	public String addComment_withAttach(CommentVO commentvo, MultipartHttpServletRequest mrequest) {
		// =========== !!! 첨부파일 업로드 시작 !!! ============ //
		MultipartFile attach = commentvo.getAttach();

		if (!attach.isEmpty()) {
			HttpSession session = mrequest.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";
			String newFileName = "";

			byte[] bytes = null;

			long FileSize = 0;

			try {
				bytes = attach.getBytes();

				String originalFilename = attach.getOriginalFilename();
				newFileName = fileManager.doFileUpload(bytes, originalFilename, path);

				commentvo.setFileName(newFileName);
				commentvo.setOrgFilename(originalFilename);

				FileSize = attach.getSize();
				commentvo.setFileSize(String.valueOf(FileSize));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// =========== !!! 첨부파일 업로드 끝 !!! ============ //
		int n = 0;
		try {
			n = service.addComment(commentvo);
			// 댓글쓰기(insert) 및 원게시물(tbl_board 테이블)에 댓글의 개수 증가(update 1씩 증가)하기
		} catch (Throwable e) {
			e.printStackTrace();
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("n", n);
		jsonObj.put("name", commentvo.getName());

		return jsonObj.toString();
	}// end of public String addComment_withAttach(CommentVO commentvo, MultipartHttpServletRequest mrequest) {------
		// ====== 댓글쓰기에 첨부파일이 있는 경우 끝 ======//

	// ====== 파일첨부가 있는 댓글쓰기에서 파일 다운로드 받기 시작====== //
	@GetMapping(value = "/downloadComment.gw")
	public void requiredLogin_downloadComment(HttpServletRequest request, HttpServletResponse response) {

		String seq = request.getParameter("seq");
		// 첨부파일이 있는 댓글의 글번호
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;

		try {
			Integer.parseInt(seq);
			CommentVO commentvo = service.getCommentOne(seq);

			if (commentvo == null || (commentvo != null && commentvo.getFileName() == null)) {
				out = response.getWriter();
				out.println(
						"<script type='text/javascript'>alert('존재하지 않는 댓글번호 이거나 첨부파일이 없으므로 파일다운로드가 불가합니다.'); history.back();</script>");
				return;
			} else {// 정상적으로 다운로드를 할 경우

				String fileName = commentvo.getFileName();

				String orgFilename = commentvo.getOrgFilename();

				HttpSession session = request.getSession();
				String root = session.getServletContext().getRealPath("/");
				String path = root + "resources" + File.separator + "files";

				// ***** file 다운로드 하기 ***** //
				boolean flag = false; // file 다운로드 성공, 실패인지 여부를 알려주는 용도
				flag = fileManager.doFileDownload(fileName, orgFilename, path, response);
				// file 다운로드 성공시 flag 는 true, file 다운로드 실패시 flag 는 false 를 가진다.

				if (!flag) {
					// 다운로드가 실패한 경우 메시지를 띄워준다.
					out = response.getWriter();
					out.println("<script type='text/javascript'>alert('파일다운로드가 실패되었습니다.'); history.back();</script>");
				}
			}

		} catch (NumberFormatException | IOException e) {
			try {
				out = response.getWriter();
				out.println("<script type='text/javascript'>alert('파일다운로드가 불가합니다.'); history.back();</script>");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}

	}// end of public void requiredLogin_downloadComment(HttpServletRequest request, HttpServletResponse response) {--------
		// ====== 파일첨부가 있는 댓글쓰기에서 파일 다운로드 받기 끝====== //
	
	// ====== 댓글을 수정하기 시작====== //
	@ResponseBody
	@PostMapping(value = "/reviewUpdate.gw", produces = "text/plain;charset=UTF-8")
	public String reviewUpdate(HttpServletRequest request) {
		// 글 수정해야 할 글번호 가져오기
		String seq = request.getParameter("seq");
		String content = request.getParameter("content");

		// String message = "";
		//System.out.println("content:" + content);
		//System.out.println("seq:" + seq);
		Integer.parseInt(seq);

		// 글 수정해야할 글1개 내용가져오기
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("seq", seq);
		paraMap.put("content", content);

		CommentVO commentvo = service.getComment_One(paraMap);
		// 단순히 댓글 1개만 조회해주는 것이다.

		int n = service.getupdate_review(paraMap, commentvo);
		boolean suc = false;

		if (n == 1) {
			suc = true;
		} else {
			suc = false;

		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("suc", suc);
		String json = jsonObj.toString();
		System.out.println(json);
		return jsonObj.toString();
	}// end of public String reviewUpdate(HttpServletRequest request) {--------------
		// ====== 댓글을 수정하기 끝====== //

	// ===== 첨부 파일 있는 댓글을 삭제하기 시작 ===== ///
	@ResponseBody
	@PostMapping(value = "/comment_Del.gw", produces = "text/plain;charset=UTF-8")
	public String comment_Del(ModelAndView mav, HttpServletRequest request) {
		String seq = request.getParameter("seq");

		Map<String, String> paraMap = new HashMap<>();

		paraMap.put("seq", seq);

		int n;
		CommentVO commentvo = service.getView_comment(paraMap);

		String fileName = commentvo.getFileName();

		if (fileName != null && !"".equals(fileName)) {
			HttpSession session = request.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";
			paraMap.put("path", path); // 삭제해야할 파일이 저장된 경로
			paraMap.put("fileName", fileName); // 삭제해야할 파일명
		}

		n = service.del_comment(paraMap, commentvo);

		boolean success = false;

		if (n == 1) {
			mav.addObject("message", "댓글 삭제 성공!!");
			mav.addObject("loc", request.getContextPath() + "/freeboard.gw");
			mav.setViewName("msg");
			success = true;
		} else {
			success = false;
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("success", success);
		String json = jsonObj.toString();
		System.out.println(json);

		return jsonObj.toString();
	}
	// end of public String comment_Del(ModelAndView mav, HttpServletRequest request) {-----
	// ===== 첨부 파일 있는 댓글을 삭제하기 끝 ===== ///

	// ===== 첨부 파일 없는 댓글을 삭제하기 시작 ===== ///
	@ResponseBody
	@PostMapping(value = "/comment_Del_nofile.gw", produces = "text/plain;charset=UTF-8")
	public String comment_Del_nofile(ModelAndView mav, HttpServletRequest request, CommentVO commentvo) {
		String seq = request.getParameter("seq");

		Map<String, String> paraMap = new HashMap<>();

		paraMap.put("seq", seq);
		commentvo = service.getView_comment(paraMap);

		int n = service.del_comment_nofile(paraMap, commentvo);

		boolean success_nofile = false;

		if (n == 1) {
			mav.addObject("message", "댓글 삭제 성공!!");
			mav.addObject("loc", request.getContextPath() + "/freeboard.gw");
			mav.setViewName("msg");
			success_nofile = true;
		} else {
			success_nofile = false;
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("success_nofile", success_nofile);
		String json = jsonObj.toString();
		System.out.println(json);

		return jsonObj.toString();

	}// end of public String comment_Del_nofile(ModelAndView mav, HttpServletRequest request, CommentVO commentvo) {
		// ===== 첨부 파일 없는 댓글을 삭제하기 끝 ===== ///

	// ===== 원게시물에 딸린 댓글들을 페이징 처리해서 조회해오기(Ajax 로 처리) 시작===== //
	@ResponseBody
	@GetMapping(value = "/commentList.gw", produces = "text/plain;charset=UTF-8")
	public String commentList(HttpServletRequest request) {
		String parentSeq = request.getParameter("parentSeq");
		String currentShowPageNo = request.getParameter("currentShowPageNo");

		if (currentShowPageNo == null) {
			currentShowPageNo = "1";
		}

		int sizePerPage = 5; // 한 페이지당 5개의 댓글을 보여줄 것임.
		int startRno = ((Integer.parseInt(currentShowPageNo) - 1) * sizePerPage) + 1; // 시작 행번호
		int endRno = startRno + sizePerPage - 1; // 끝 행번호

		Map<String, String> paraMap = new HashMap<>();

		paraMap.put("parentSeq", parentSeq);
		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));

		List<CommentVO> commentList = service.getCommentList_Paging(paraMap);
		JSONArray jsonArr = new JSONArray();

		if (commentList != null) {
			for (CommentVO cmtvo : commentList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("name", cmtvo.getName());
				jsonObj.put("content", cmtvo.getContent());
				jsonObj.put("regdate", cmtvo.getRegDate());
				jsonObj.put("fk_email", cmtvo.getFk_email());
				// ==== 댓글읽어오기에 있어서 첨부파일 기능을 넣은 경우 시작 ==== //
				jsonObj.put("seq", cmtvo.getSeq());
				jsonObj.put("fileName", cmtvo.getFileName());
				jsonObj.put("orgFilename", cmtvo.getOrgFilename());
				jsonObj.put("fileSize", cmtvo.getFileSize());
				// ==== 댓글읽어오기에 있어서 첨부파일 기능을 넣은 경우 끝 ==== //
				jsonArr.put(jsonObj);

			} // end of for-----------------------------------
		}
		return jsonArr.toString();
	}// end of public String commentList(HttpServletRequest request) { -------------
		// ===== 원게시물에 딸린 댓글들을 페이징 처리해서 조회해오기(Ajax 로 처리) 끝 ===== //

	// === 원게시물에 딸린 댓글의 totalPage 수 알아오기(JSON 으로 처리) 시작 === //
	@ResponseBody
	@GetMapping(value = "/getCommentTotalPage.gw")
	public String CommentTotalPage(HttpServletRequest request) {

		String parentSeq = request.getParameter("parentSeq");
		String sizePerPage = request.getParameter("sizePerPage");

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("parentSeq", parentSeq);
		paraMap.put("sizePerPage", sizePerPage);

		// 원글 글번호(parentSeq)에 해당하는 댓글의 totalPage 수 알아오기
		int totalPage = service.getCommentTotalPage(paraMap);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("totalPage", totalPage);

		return jsonObj.toString();
	} // end of public String CommentTotalPage(HttpServletRequest request) {------------
		// === 원게시물에 딸린 댓글의 totalPage 수 알아오기(JSON 으로 처리) 끝 === //
	
	
	
	// ========== 공지사항 리스트 페이지 만들기 시작 =============
	@GetMapping("/noticeboard.gw")
	public ModelAndView noticeboard(ModelAndView mav, HttpServletRequest request) {

		List<NoticeboardVO> boardList = null;

		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");

		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");

		if (searchType == null) {
			searchType = "";
		}

		if (searchWord == null) {
			searchWord = "";
		}

		if (searchWord != null) {
			searchWord = searchWord.trim();
		}

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);

		int totalCount = 0; // 총 게시물 건수
		int sizePerPage = 10; // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo = 0; // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0; // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)

		// 공지사항의 총 게시물 건수(totalCount) 가져오기
		totalCount = service.getNoticeTotalCount(paraMap);

		totalPage = (int) Math.ceil((double) totalCount / sizePerPage);

		if (str_currentShowPageNo == null) {
			currentShowPageNo = 1;
		}

		else {
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				if (currentShowPageNo < 1 || currentShowPageNo > totalPage) {
					currentShowPageNo = 1;
				}
			} catch (NumberFormatException e) {
				currentShowPageNo = 1;
			}
		} // end of else{------------------------

		int startRno = ((currentShowPageNo - 1) * sizePerPage) + 1; // 시작 행번호
		int endRno = startRno + sizePerPage - 1; // 끝 행번호

		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));

		boardList = service.noticeListSearch_withPaging(paraMap);
		// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함 한 것)

		mav.addObject("boardList", boardList);

		if ("subject".equals(searchType) || "content".equals(searchType) || "subject_content".equals(searchType)
				|| "name".equals(searchType)) {

			mav.addObject("paraMap", paraMap);

		}

		// ===== 페이지바 만들기 ===== //
		int blockSize = 10;
		int loop = 1;
		int pageNo = ((currentShowPageNo - 1) / blockSize) * blockSize + 1;

		String pageBar = "<ul style='list-style:none;'>";
		String url = "noticeboard.gw";

		// === [맨처음][이전] 만들기 === //
		if (pageNo != 1) {
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a style='color:black;' href='" + url + "?searchType="
					+ searchType + "&searchWord=" + searchWord + "&currentShowPageNo=1'>[맨처음]</a></li>";
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a style='color:black;' href='" + url + "?searchType="
					+ searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + (pageNo - 1)
					+ "'>[이전]</a></li>";
		}

		while (!(loop > blockSize || pageNo > totalPage)) {

			if (pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:black; padding:2px 4px;'>"
						+ pageNo + "</li>";
			}

			else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a style='color:black;' href='" + url
						+ "?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + pageNo
						+ "'>" + pageNo + "</a></li>";
			}

			loop++;
			pageNo++;

		} // end of while-------------------------

		// ===== [다음][마지막] 만들기 ===== //
		if (pageNo <= totalPage) {
			pageBar += "<li class='icon_text' style='display:inline-block; width:50px; font-size:8pt;'><a class='material-icons-outlined icon_img' style='font-size: 12pt; color:black;' href='" + url + "?searchType="
					+ searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + pageNo + "'>[다음]</a></li>";
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a style='color:black;' href='" + url + "?searchType="
					+ searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + totalPage + "'>[마지막]</a></li>";
		}

		pageBar += "</ul>";

		mav.addObject("pageBar", pageBar);

		// === 특정 글제목을 클릭한 뒤 상세내용을 본 이후 사용자가 "검색된결과목록보기" 버튼을 클릭했을때 현재 페이지 주소를 뷰단으로 넘겨준다.
		String goBackURL = MyUtil.getCurrentURL(request);

		mav.addObject("goBackURL", goBackURL);
		mav.setViewName("mshboard/noticeboard.tiles_MTS");

		return mav;

	} // end of public ModelAndView mainboard(ModelAndView mav,HttpServletRequest request) {-----------

	// ========== 공지사항 리스트 페이지 만들기 끝 =============

	// ===== 공지사항 쓰기 페이지 요청 시작 ===== ///
	@GetMapping("/noticeadd.gw")
	public ModelAndView notice_add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {

		mav.setViewName("mshboard/noticeadd.tiles_MTS");
		
		return mav;
	}// end of public ModelAndView notice_add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) { ------------
	// ===== 공지사항 쓰기 페이지 요청 끝 ===== //
	
	// ====== 게시판 글쓰기 완료 요청 ======= //
	@PostMapping("/noticeaddEnd.gw")
	public ModelAndView noticeaddEnd(Map<String, String> paraMap, ModelAndView mav, NoticeboardVO boardvo, MultipartHttpServletRequest mrequest) {
		// ===== 첨부파일이 있는 경우 작업 시작 !!! ======
		MultipartFile attach = boardvo.getAttach();

		if (attach != null) {
			// attach(첨부파일)가 비어 있지 않으면(즉, 첨부파일이 있는 경우라면)
			HttpSession session = mrequest.getSession();

			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";
			String newFileName = "";

			byte[] bytes = null;
			long FileSize = 0;

			try {
				bytes = attach.getBytes();

				String originalFilename = attach.getOriginalFilename();
				newFileName = fileManager.doFileUpload(bytes, originalFilename, path);

				boardvo.setFile_Name(newFileName);
				boardvo.setOrg_Filename(originalFilename);

				FileSize = attach.getSize(); // 첨부파일의 크기(단위는 byte임)
				boardvo.setFile_Size(String.valueOf(FileSize));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ===== !!! 첨부파일이 있는 경우 작업 끝 !!! =====

		int n = 0;

		if (attach.isEmpty()) {
			// 파일첨부가 없는 경우라면
			n = service.notice_add(boardvo);
		} 
		
		else {
			// 파일첨부가 있는 경우라면
			n = service.notice_add_withFile(boardvo);
		}
		
		if (n == 1) {
			mav.setViewName("redirect:/noticeboard.gw");
		} else {
			mav.setViewName("redirect:/index.gw");
		}

		paraMap.put("fk_emp_id", boardvo.getFk_emp_id());

		return mav;
	} // end of public ModelAndView addEnd(Map<String, String> paraMap, ModelAndView mav, BoardVO boardvo, MultipartHttpServletRequest mrequest) { ----------
	// ====== 게시판 글쓰기 완료 요청 끝======= //
	
	// ====== 공지사항 1개를 보여주는 페이지 요청하기 시작 ====== //
	@RequestMapping("/notice_view.gw") // ==== 특정글을 조회한 후 "검색된결과목록보기" 버튼을 클릭했을 때 돌아갈 페이지를 만들기 위함.
	public ModelAndView notice_view(ModelAndView mav, HttpServletRequest request) {
		
		String seq = "";
		String goBackURL = "";
		String searchType = "";
		String searchWord = "";

		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		// redirect 되어서 넘어온 데이터가 있는지 꺼내어 와본다.

		if (inputFlashMap != null) {

			@SuppressWarnings("unchecked") // 경고 표시를 하지 말라는 뜻이다.
			Map<String, String> redirect_map = (Map<String, String>) inputFlashMap.get("redirect_map");
			seq = redirect_map.get("seq");
			searchType = redirect_map.get("searchType");

			try {
				searchWord = URLDecoder.decode(redirect_map.get("searchWord"), "UTF-8"); // 한글데이터가 포함되어 있으면 반드시 한글로
																							// 복구주어야 한다.
				goBackURL = URLDecoder.decode(redirect_map.get("goBackURL"), "UTF-8"); // 한글데이터가 포함되어 있으면 반드시 한글로 복구주어야
																						// 한다.
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (goBackURL != null && goBackURL.contains(" ")) {
				goBackURL = goBackURL.replaceAll(" ", "&");
			}
		}

		else {
			seq = request.getParameter("seq");

			goBackURL = request.getParameter("goBackURL");

			if (goBackURL != null && goBackURL.contains(" ")) {
				goBackURL = goBackURL.replaceAll(" ", "&");
			}

			// === 검색으로 조회된 글을 볼 때 view.jsp 에서 이전글제목 및 다음글제목 클릭시 사용하기 위한 것임. 시작 ===
			searchType = request.getParameter("searchType");
			searchWord = request.getParameter("searchWord");

			if (searchType == null) {
				searchType = "";
			}

			if (searchWord == null) {
				searchWord = "";
			}
			// === 검색으로 조회된 글을 볼 때 view.jsp 에서 이전글제목 및 다음글제목 클릭시 사용하기 위한 것임. 끝 ===
		}

		mav.addObject("goBackURL", goBackURL);

		try {
			Integer.parseInt(seq);
			HttpSession session = request.getSession();
			EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
			String login_Employee_id = null;

			if (loginuser != null) {
				login_Employee_id = loginuser.getEmployee_id();
			}

			Map<String, String> paraMap = new HashMap<>();

			paraMap.put("seq", seq);
			paraMap.put("login_Employee_id", login_Employee_id);

			paraMap.put("searchType", searchType);
			paraMap.put("searchWord", searchWord);

			NoticeboardVO boardvo = null;

			if ("yes".equals((String) session.getAttribute("readCountPermission"))) {
				// 글목록보기인 /freeboard.gw 페이지를 클릭한 다음에 특정글을 조회해온 경우이다.
				boardvo = service.getNoticeView(paraMap);
				// 글조회수 증가와 함께 글1개를 조회를 해주는 것

				session.removeAttribute("readCountPermission");
			}
			/*
			else {
				// 글목록에서 특정 글제목을 클릭하여 본 상태에서 웹브라우저에서 새로고침(F5)을 클릭한 경우이다.
				boardvo = service.getView_no_increase_readCount(paraMap);
				// 글조회수 증가는 없고 단순히 글1개만 조회를 해주는 것
			}
			*/
			mav.addObject("boardvo", boardvo);
			mav.addObject("paraMap", paraMap);

		} catch (NumberFormatException e) {
			// 이전글제목 또는 다음글제목을 클릭하여 본 상태에서
			// 웹브라우저에서 새로고침(F5)을 클릭한 경우이다.
			mav.setViewName("redirect:/noticeboard.gw");
			return mav;
		}

		mav.setViewName("mshboard/notice_view.tiles_MTS");
		return mav;
	}// end of public ModelAndView view(ModelAndView mav, HttpServletRequest request) {-------------

	// ====== 글1개를 보여주는 페이지 요청하기 끝 ====== //

	// ====== 공지사항 1개를 보여주는 페이지 요청하기 시작 ====== //
	@RequestMapping("/notice_view_2.gw")
	public ModelAndView notice_view_2(ModelAndView mav, HttpServletRequest request, RedirectAttributes redirectAttr) {

		String seq = request.getParameter("seq");

		String goBackURL = request.getParameter("goBackURL");
		goBackURL = goBackURL.replaceAll("&", " ");

		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");

		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");

		try {
			searchWord = URLEncoder.encode(searchWord, "UTF-8");
			goBackURL = URLEncoder.encode(goBackURL, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// ==== GET 방식이 아닌 POST 방식처럼 데이터를 넘기려면 RedirectAttributes 를 사용하면 된다. 시작 ==== //
		Map<String, String> redirect_map = new HashMap<>();
		redirect_map.put("seq", seq);
		redirect_map.put("goBackURL", goBackURL);
		redirect_map.put("searchType", searchType);
		redirect_map.put("searchWord", searchWord);

		redirectAttr.addFlashAttribute("redirect_map", redirect_map);

		mav.setViewName("redirect:/notice_view.gw");
		// ==== GET 방식이 아닌 POST 방식처럼 데이터를 넘기려면 RedirectAttributes 를 사용하면 된다. 끝 ==== //

		return mav;

	}// end of public ModelAndView notice_view_2(ModelAndView mav, HttpServletRequest request, RedirectAttributes redirectAttr) {-----
	// ====== 공지사항 1개를 보여주는 페이지 요청하기 끝 ====== //
	
	
	// ===== 공지사항 글을 삭제하는 페이지 삭제하기 시작===== //
	@GetMapping("/notice_del.gw")
	public ModelAndView notice_del(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {

		// 삭제해야 할 글번호 가져오기
		String seq = request.getParameter("seq");
		String message = "";

		try {
			Integer.parseInt(seq);
			// 삭제해야할 글1개 내용가져오기

			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("seq", seq);

			NoticeboardVO boardvo = service.getnotice_View_no_increase_readCount(paraMap);
			// 글조회수(readCount) 증가 없이 단순히 글 1개만 조회해주는 것이다.

			if (boardvo == null) {
				message = "글 삭제가 불가합니다";
			} else {
				HttpSession session = request.getSession();
				EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");

					// 자신의 글을 삭제할 경우
					mav.addObject("boardvo", boardvo);
					mav.setViewName("mshboard/notice_del.tiles_MTS");

					return mav;

			} // end of try {----------------
		} catch (NumberFormatException e) {
			message = "글 삭제가 불가합니다";
		} // end of else{-------------

		String loc = "javascript:history.back()";
		mav.addObject("message", message);
		mav.addObject("loc", loc);

		mav.setViewName("msg");

		return mav;
	}// end of public ModelAndView notice_del(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {---
	// ===== 공지사항 글을 삭제하는 페이지 요청하기 끝 ===== //

	// ====== 글을 삭제하는 페이지 완료하기 시작====== //
	@PostMapping("/notice_del_End.gw")
	public ModelAndView notice_delEnd(ModelAndView mav, HttpServletRequest request) {

		String seq = request.getParameter("seq");

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("seq", seq);

		// === 파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 시작 === //
		paraMap.put("searchType", "");
		paraMap.put("searchWord", "");

		NoticeboardVO boardvo = service.getNoticeView(paraMap);
		String file_Name = boardvo.getFile_Name();

		if (file_Name != null && !"".equals(file_Name)) {

			HttpSession session = request.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";

			paraMap.put("path", path); // 삭제해야할 파일이 저장된 경로
			paraMap.put("file_Name", file_Name); // 삭제해야할 파일명
		}
		// === 파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. 끝 === //

		int n = service.notice_del(paraMap);

		if (n == 1) {
			mav.addObject("message", "글 삭제 성공!!");
			mav.addObject("loc", request.getContextPath() + "/noticeboard.gw");
			mav.setViewName("msg");
		}

		return mav;
	}// end of public ModelAndView notice_delEnd(ModelAndView mav, HttpServletRequest request) {------
	// ====== 글을 삭제하는 페이지 완료하기 끝===== //
	

	
	
	// ===== 공지사항 글을 수정하는 페이지  시작===== //
	@GetMapping("/notice_edit.gw")
	public ModelAndView notice_edit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {

		// 글 수정해야 할 글번호 가져오기
		String seq = request.getParameter("seq");
		//System.out.println(seq);
		String message = "";
			try {
				Integer.parseInt(seq);
				// 글 수정해야할 글1개 내용가져오기
				Map<String, String> paraMap = new HashMap<>();
				paraMap.put("seq", seq);
				NoticeboardVO boardvo = service.getNoticeView(paraMap);
				// 글조회수(readCount) 증가 없이 단순히 글 1개만 조회해주는 것이다.

				if (boardvo == null) {
					message = "글 수정이 불가합니다";
				}

				else {
					HttpSession session = request.getSession();
					EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
					
						// 자신의 글을 수정할 경우
						mav.addObject("boardvo", boardvo);
						mav.setViewName("mshboard/notice_edit.tiles_MTS");

						return mav;
				
				}
			} catch (NumberFormatException e) {
				message = "글 수정이 불가합니다";
			}
			String loc = "javascript:history.back()";

			mav.addObject("message", message);
			mav.addObject("loc", loc);
			mav.setViewName("msg");

			return mav;
	}// end of public ModelAndView notice_edit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {---
	// ===== 공지사항 글을 수정하는 페이지 요청하기 끝 ===== //
	
	
	@GetMapping("/editAfter_notice_view.gw")
	public ModelAndView editAfter_notice_view(ModelAndView mav, NoticeboardVO boardvo, HttpServletRequest request) {
		
		String seq = "";
		String goBackURL = "";
		String searchType = "";
		String searchWord = "";

		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		// redirect 되어서 넘어온 데이터가 있는지 꺼내어 와본다.

		if (inputFlashMap != null) {

			@SuppressWarnings("unchecked") // 경고 표시를 하지 말라는 뜻이다.
			Map<String, String> redirect_map = (Map<String, String>) inputFlashMap.get("redirect_map");
			seq = redirect_map.get("seq");
			searchType = redirect_map.get("searchType");

			try {
				searchWord = URLDecoder.decode(redirect_map.get("searchWord"), "UTF-8"); // 한글데이터가 포함되어 있으면 반드시 한글로
																							// 복구주어야 한다.
				goBackURL = URLDecoder.decode(redirect_map.get("goBackURL"), "UTF-8"); // 한글데이터가 포함되어 있으면 반드시 한글로 복구주어야
																						// 한다.
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (goBackURL != null && goBackURL.contains(" ")) {
				goBackURL = goBackURL.replaceAll(" ", "&");
			}
		}

		else {
			seq = request.getParameter("seq");
			goBackURL = request.getParameter("goBackURL");

			if (goBackURL != null && goBackURL.contains(" ")) {
				goBackURL = goBackURL.replaceAll(" ", "&");
			}

			// === 검색으로 조회된 글을 볼 때 view.jsp 에서 이전글제목 및 다음글제목 클릭시 사용하기 위한 것임. 시작 ===
			searchType = request.getParameter("searchType");
			
			searchWord = request.getParameter("searchWord");

			if (searchType == null) {
				searchType = "";
			}

			if (searchWord == null) {
				searchWord = "";
			}
			System.out.println("넘어온다"+searchType);
			// === 검색으로 조회된 글을 볼 때 view.jsp 에서 이전글제목 및 다음글제목 클릭시 사용하기 위한 것임. 끝 ===
		}

		mav.addObject("goBackURL", goBackURL);

		try {
			Integer.parseInt(seq);
			HttpSession session = request.getSession();
			EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
			String login_Employee_id = null;

			if (loginuser != null) {
				login_Employee_id = loginuser.getEmployee_id();
			}

			Map<String, String> paraMap = new HashMap<>();

			paraMap.put("seq", seq);
			paraMap.put("login_Employee_id", login_Employee_id);

			paraMap.put("searchType", searchType);
			paraMap.put("searchWord", searchWord);

			// 글목록에서 특정 글제목을 클릭하여 본 상태에서 웹브라우저에서 새로고침(F5)을 클릭한 경우이다.
			boardvo = service.getnotice_View_no_increase_readCount(paraMap);
		
			mav.addObject("boardvo", boardvo);
			mav.addObject("paraMap", paraMap);

		} catch (NumberFormatException e) {
			// 이전글제목 또는 다음글제목을 클릭하여 본 상태에서
			// 웹브라우저에서 새로고침(F5)을 클릭한 경우이다.
			mav.setViewName("redirect:/noticeboard.gw");
			return mav;
		}

		mav.setViewName("mshboard/notice_view.tiles_MTS");
		return mav;
	}//end of public ModelAndView editAfter_notice_view(ModelAndView mav, NoticeboardVO boardvo, HttpServletRequest request) {
	
	// ===== 검색어 입력시 자동글 완성하기 시작 ===== //
	@ResponseBody
	@GetMapping(value = "/notice_wordSearchShow.gw", produces = "text/plain;charset=UTF-8")
	public String notice_wordSearchShow(HttpServletRequest request) {

		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);

		List<String> wordList = service.notice_wordSearchShow(paraMap);

		JSONArray jsonArr = new JSONArray(); // []

		if (wordList != null) {
			for (String word : wordList) {
				JSONObject jsonObj = new JSONObject(); // {}
				jsonObj.put("word", word);
				jsonArr.put(jsonObj); // [{},{},{}]
			} // end of for------------
		}

		return jsonArr.toString();
	}
	// ===== 검색어 입력시 자동글 완성하기 끝 ===== //
	
	// ========== 공지사항을 수정하는 페이지에서 첨부파일 삭제 시작 =============//
	@ResponseBody
	@GetMapping(value = "/notice_delete_file.gw", produces = "text/plain;charset=UTF-8")
    public String notice_deletefile(ModelAndView mav, HttpServletRequest request) {
		String seq = request.getParameter("seq");

		Map<String, String> paraMap = new HashMap<>();

		paraMap.put("seq", seq);

		int n;
		NoticeboardVO boardvo = service.getnotice_View_no_increase_readCount(paraMap);

		String file_Name = boardvo.getFile_Name();

		if (file_Name != null && !"".equals(file_Name)) {
			HttpSession session = request.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";
			paraMap.put("path", path); // 삭제해야할 파일이 저장된 경로
			paraMap.put("file_Name", file_Name); // 삭제해야할 파일명
		}

		n = service.notice_delete_file(paraMap, boardvo);

		boolean success = false;

		if (n == 1) {
			mav.addObject("message", "파일 삭제 성공!!");
			mav.addObject("loc", request.getContextPath() + "/noticeboard.gw");
			mav.setViewName("msg");
			success = true;
		} else {
			success = false;
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("success", success);
		String json = jsonObj.toString();
		System.out.println(json);

		return jsonObj.toString();
	}// end of public String notice_deletefile(ModelAndView mav, HttpServletRequest request) { ---------
		
	// ========== 공지사항을 수정하는 페이지에서 첨부파일 삭제 끝 =============//
	
	// ===== 글을 수정하는 페이지 완료하기 시작===== //
	@PostMapping(value = "/notice_editEnd.gw", produces = "text/plain;charset=UTF-8")
	public ModelAndView notice_editEnd(Map<String, String> paraMap, ModelAndView mav, NoticeboardVO boardvo, HttpServletRequest request,
			MultipartHttpServletRequest mrequest) {
		
		MultipartFile attach = boardvo.getAttach();

		if (attach != null && !attach.isEmpty()) {
			// attach(첨부파일)가 비어 있지 않으면(즉, 첨부파일이 있는 경우라면)
			HttpSession session = mrequest.getSession();

			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";

			byte[] bytes = null;
			long File_Size = 0;

			try {
				bytes = attach.getBytes();

				String originalFilename = attach.getOriginalFilename();
				String newFileName = fileManager.doFileUpload(bytes, originalFilename, path);
				boardvo.setFile_Name(newFileName);
				boardvo.setOrg_Filename(originalFilename);

				File_Size = attach.getSize(); // 첨부파일의 크기(단위는 byte임)
				boardvo.setFile_Size(String.valueOf(File_Size));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ===== !!! 첨부파일이 있는 경우 작업 끝 !!!
		
		int n = 0;
		
		if (attach == null || attach.isEmpty() ) {
			n = service.notice_edit(boardvo);
		} 
				
		if(attach != null && !attach.isEmpty() ){
		// 파일첨부가 있는 경우라면
			n = service.notice_edit_withFile(boardvo);
		}


		if (n == 1) {
			mav.addObject("message", "글 수정 성공!!");
			mav.addObject("loc", request.getContextPath() + "/editAfter_notice_view.gw?seq="+boardvo.getSeq());
			mav.setViewName("msg");
		}
		return mav;
	}// end of public ModelAndView editEnd(ModelAndView mav, BoardVO boardvo, HttpServletRequest request) {--------
	// ===== 글을 수정하는 페이지 완료하기 끝===== //
	
	// ====== 공지사항 첨부파일 다운로드 받기 시작 ======= //
	@GetMapping(value = "/notice_download.gw")
	public void notice_download(HttpServletRequest request, HttpServletResponse response) {

		String seq = request.getParameter("seq");
		// 첨부파일이 있는 글번호

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("seq", seq);
		paraMap.put("searchType", "");
		paraMap.put("searchWord", "");

		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = null;

		try {
			Integer.parseInt(seq);
			NoticeboardVO boardvo = service.getnotice_View_no_increase_readCount(paraMap);

			if (boardvo == null || (boardvo != null && boardvo.getFile_Name() == null)) {
				out = response.getWriter();
				out.println(
						"<script type='text/javascript'>alert('존재하지 않는 글번호 이거나 첨부파일이 없으므로 파일다운로드가 불가합니다.'); history.back();</script>");
				return;
			} else { // 정상적으로 다운로드를 할 경우
				String file_Name = boardvo.getFile_Name();
				String org_Filename = boardvo.getOrg_Filename();
				HttpSession session = request.getSession();
				String root = session.getServletContext().getRealPath("/");
				String path = root + "resources" + File.separator + "files";

				// ***** file 다운로드 하기 ***** //
				boolean flag = false; // file 다운로드 성공, 실패인지 여부를 알려주는 용도
				flag = fileManager.doFileDownload(file_Name, org_Filename, path, response);
				// file 다운로드 성공시 flag 는 true, 실패시 flag 는 false 를 가진다.
				if (!flag) {
					// 다운로드가 실패한 경우 메시지를 띄워준다.
					out = response.getWriter();
					out.println("<script type='text/javascript'>alert('파일다운로드가 실패되었습니다.'); history.back();</script>");
				}
			}

		} catch (NumberFormatException | IOException e) {
			try {
				out = response.getWriter();
				out.println("<script type='text/javascript'>alert('파일다운로드가 불가합니다.'); history.back();</script>");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}

	}// end of public ModelAndView notice_dowonload(Map<String, String> paraMap, ModelAndView mav, NoticeboardVO boardvo, HttpServletRequest request,MultipartHttpServletRequest mrequest) {
	// ====== 공지사항 첨부파일 다운로드 받기 끝 ======= //
	
	@GetMapping(value = "/digital.gw")
	public ModelAndView digital(ModelAndView mav) {

		mav.setViewName("mshboard/digitalmailview.tiles_MTS");
		return(mav);
	}
	
	
	
	
	
	
}
