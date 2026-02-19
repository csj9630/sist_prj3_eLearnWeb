package kr.co.sist.user.lecture.test;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class StuTestService {
	
	@Autowired(required = false)
	private StuTestMapper stMapper;
	
	//사용자에게 시험 문제 보여주기
	public List<StuTestViewDomain> searchTest(String lectId) {
		List<StuTestViewDomain> stDomainList = null;
		try {
			stDomainList = stMapper.selectUserTest(lectId);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} // end catch
		return stDomainList;
	}//searchTest
	
	
	// 사용자 시험 응시 유무 확인
		public boolean searchCheckTest(StuCheckTestDTO sctDTO) {
			boolean flag = false;
			try {
				if(stMapper.selectCheckTest(sctDTO) != 0) {
					flag = true;
				}
			} catch (PersistenceException pe) {
				pe.printStackTrace();
			} // end catch
			return flag;
		}// searchCheckTest
		
		// 시험 응시 기록 리셋
		@Transactional
		public boolean resetTestRecord(StuCheckTestDTO sctDTO) {
			boolean flag = false;
			try {
				stMapper.deleteTestExamination(sctDTO); 
				stMapper.deleteMyTest(sctDTO); 
				
				flag = true;
				
			} catch (PersistenceException pe) {
				pe.printStackTrace();
			} // end catch
			return flag;
		}// resetTestRecord
		
		//사용자 응시 기록 존재할 경우 과거 체크했던 정답 및 데이터 가져오기.
		public List<StuTestExplanationDomain> searchExplanation(StuCheckTestDTO sctDTO) {
		    List<StuTestExplanationDomain> list = null;
		    try {
		        list = stMapper.selectUserExplanation(sctDTO);
		    } catch (PersistenceException pe) {
		        pe.printStackTrace();
		    }
		    return list;
		}
	
}//StuTestService
