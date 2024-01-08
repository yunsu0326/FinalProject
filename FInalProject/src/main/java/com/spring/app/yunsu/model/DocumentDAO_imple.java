package com.spring.app.yunsu.model;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.spring.app.domain.DocumentVO;

@Repository
public class DocumentDAO_imple implements DocumentDAO {
	
	@Resource
	private SqlSessionTemplate sqlsession;
	
	//회원정보 가져오기
	@Override
	public Map<String, String> documentDown(String employee_id) {
		Map<String, String> paraMap = sqlsession.selectOne("yunsu.documentDown", employee_id);
		return paraMap;
	}
	
	//문서 다운로드 정보 넣기
	@Override
	public int documentInsert(Map<String, String> paraMap) {
		int n =sqlsession.insert("yunsu.documentInsert", paraMap);
		return n;
	}
	
	//문서 정보 넣기
	@Override
	public int insertDocument(DocumentVO documentvo) {
		int n = sqlsession.insert("yunsu.insertDocument",documentvo);
		return n;
	}
	
	//문서 정보 가져오기
	@Override
	public List<Map<String, String>> selectDocument() {
		List<Map<String, String>> documentList =  sqlsession.selectList("yunsu.selectDocument");
		return documentList;
	}

	@Override
	public String deleteDocumentSelect(String seq) {
		String fileName = sqlsession.selectOne("yunsu.deleteDocumentSelect", seq);
		return fileName;
	}

	@Override
	public int deleteDocument(Map<String, String> paraMap) {
		int n = sqlsession.delete("yunsu.deleteDocument", paraMap); 
		return n;
	}
	
	//다운받을 문서 조회
	@Override
	public DocumentVO viewDocument(Map<String, String> paraMap) {
		DocumentVO documentvo = sqlsession.selectOne("yunsu.viewDocument", paraMap);
		return documentvo;
	}
	
	// 다운 기록 넣기
	@Override
	public int insertDownLoadrecord(Map<String, String> paraMap) {
		int n = sqlsession.insert("yunsu.insertDownLoadrecord", paraMap);
		return n;
	}
	
	//수정을 위해 삭제할 파일명 가져오기
	@Override
	public String selectUpdateDeleteFile(DocumentVO documentvo) {
		String fileName = sqlsession.selectOne("yunsu.selectUpdateDeleteFile", documentvo);
		return fileName;
	}
	
	// 문서 테이블 업데이트
	@Override
	public int updateDocument(DocumentVO documentvo) {
		int n = sqlsession.update("yunsu.updateDocument", documentvo);
		return n;
	}
	
	

}
