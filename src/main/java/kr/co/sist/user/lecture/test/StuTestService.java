package kr.co.sist.user.lecture.test;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class StuTestService {
	
	@Autowired(required = false)
	private StuTestMapper stMapper;
	
	//사용자에게 시험 문제 보여주기
	public List<StuTestDomain> searchTest(String lectId) {
		List<StuTestDomain> stDomainList = null;
		try {
			stDomainList = stMapper.selectUserTest(lectId);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} // end catch
		return stDomainList;
	}//searchTest
	
	//시험문제 - 삭제
	public int removeTest(String testId) {
		int result = 0;
		
		return result;
	}
	
	//시험문제 - 수정
	public int modifyTest(StuTestDTO stDTO) {
		int result = 0;
		
		return result;
	}
	//시험문제 - 생성
	public int writeTest(StuTestDTO stDTO) {
		int result = 0;
		
		return result;
	}
	
}
