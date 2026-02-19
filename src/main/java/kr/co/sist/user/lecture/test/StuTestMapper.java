package kr.co.sist.user.lecture.test;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

@Mapper
public interface StuTestMapper {
	// 시험 문제 조회 후 사용자 화면에 뿌려주기
	public List<StuTestViewDomain> selectUserTest(String lectId) throws PersistenceException;
	
	// 시험 문제 제출 후 정답 및 해설 가져와서 비교 후 체점하기
	public List<StuTestAnswerDomain> selectUserQuestionAnswer(String lectId) throws PersistenceException;
	
	// 시험 이력 존재시, 해설 페이지 진입
	public List<StuTestExplanationDomain> selectUserExplanation(StuCheckTestDTO sctDTO) throws PersistenceException;

	// 내 시험이력 테이블에 값 저장
	public int insertMyTest(StuTestRecordDTO strDTO) throws PersistenceException;
	
	// 시험응시 테이블에 값 저장
	public int insertTestExamination(Map<String, Object> tempMap) throws PersistenceException;
	
	// 시험 응시 유무 체크
	public int selectCheckTest(StuCheckTestDTO sctDTO) throws PersistenceException;
	
	// 시험응시 테이블에서 응시 기록 삭제
	public void deleteTestExamination(StuCheckTestDTO sctDTO) throws PersistenceException;
	
	// 내 시험이력 테이블에서 응시 기록 삭제
	public void deleteMyTest(StuCheckTestDTO sctDTO) throws PersistenceException;
}
