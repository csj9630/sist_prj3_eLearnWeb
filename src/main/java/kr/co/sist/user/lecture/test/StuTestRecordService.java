package kr.co.sist.user.lecture.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StuTestRecordService {

	@Autowired(required = false)
	private StuTestMapper stMapper;

	// 내 시험 점수 조회
	public StuMyTestDTO searchMyTestScore(String lectId) {
		StuMyTestDTO sDTO = null;

		return sDTO;
	}// searchMyTestScore

	//내시험이력 테이블에 값 삽입
	public boolean insertMyTest(StuTestRecordDTO strDTO) {
		boolean flag = false;
		try {
			if (stMapper.insertMyTest(strDTO) == 1) {
				flag = true;
			}
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} // end catch

		return flag;
	}
	
	//시험 응시 테이블에 값 삽입
	@Transactional
	public boolean insertTestExamination(StuAnswerDTO saDTO) {
		boolean flag = false;
		try {
			for(int i = 0; i < saDTO.getTest_q_num().size(); i++) {
				//지금 값들은 Map 안에 List로 저장되어 있음. 5개의 정답을 체크 했으면 각 맵에 5개의 값이 있음. 근데 나는 DTO에서 Map으로 저장했으니 값을 Map인데 1개씩 빼서 해야함
				Map<String , Object> tempMap = new HashMap<String, Object>();
				tempMap.put("test_q_num", saDTO.getTest_q_num().get(i));
				tempMap.put("my_test_id", saDTO.getMy_test_id());
				tempMap.put("choice", saDTO.getChoice().get(i));
				stMapper.insertTestExamination(tempMap);
			}
			return flag = true;
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return flag = false;
		} // end catch
	}

	// 사용자 - 시험 문제 제출 -> 정답 비교 후(채점) 해설 및 정답 가져오기
	public List<StuTestAnswerDomain> searchQuestionAnswer(String lectId, StuAnswerDTO saDTO, StuTestRecordDTO strDTO) {
		List<StuTestAnswerDomain> staDomainList = null;
		try {
			// 정답 및 해설 가져오기.
			staDomainList = stMapper.selectUserQuestionAnswer(lectId);
			//채점 후 점수를 DTO에 입력.
			strDTO.setScore(grade(staDomainList, saDTO, strDTO));
			
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} // end catch
		return staDomainList;
	}// searchQuestionAnswer

	// 채점용 메소드
	public int grade(List<StuTestAnswerDomain> staDomainList, StuAnswerDTO saDTO, StuTestRecordDTO strDTO) {
		int score = 0;
		
		Map<String, Integer> checkAnswerMap = new HashMap<String, Integer>();
		
		// 사용자가 입력한 정답과 qid를 Map으로 연결 후 채점 진행.(혹시라도 문제 순서가 바뀔 경우 확실학 채점하기 위해서
		for (int i = 0; i < saDTO.getChoice().size(); i++) {
			checkAnswerMap.put(saDTO.getQid().get(i), saDTO.getChoice().get(i));
		}
		//정답 수 만큼 반복.
		for (int i = 0; i < staDomainList.size(); i++) {
			if ( checkAnswerMap.get(staDomainList.get(i).getQid()).equals(staDomainList.get(i).getAns()) ) {
				score += 20;
			}//end if 
		}//end for
		return score;
	}

	// 내 시험 답안 조회
	public List<StuTestAnswerDomain> searchMyTestAnswer(String userId, String TestId) {
		List<StuTestAnswerDomain> staDomainList = null;

		stMapper.selectUserQuestionAnswer(TestId);

		return staDomainList;
	}// searchMyTestAnswer

	// 내 작성 답안 제출 및 저장
	public int submitMyTest(StuMyTestDTO stDTO) {
		int i = 0;

		return i;
	}// submitMyTest
}
