package kr.co.sist.user.lecture.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StuTestRecordService {
	
	@Autowired(required = false)
	private StuTestMapper stMapper;
	
	//내 시험 점수 조회
	public StuTestDTO searchMyTestScore(String lectId) {
		StuTestDTO sDTO = null;
		
		return sDTO;
	}//searchMyTestScore
	
	//내 시험 답안 조회
	public List<StuTestDomain> searchMyTestAnswer(String userId, String TestId) {
		List<StuTestDomain> stDomainList = null;
		
		return stDomainList;
	}//searchMyTestAnswer
	
	//내 작성 답안 제출 및 저장
	public int submitMyTest(StuTestDTO stDTO) {
		int i = 0;
		
		return i;
	}//submitMyTest
}
