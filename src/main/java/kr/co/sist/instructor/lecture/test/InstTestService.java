package kr.co.sist.instructor.lecture.test;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class InstTestService {
	
	@Autowired(required=false)
	private InstTestMapper iMapper;
	
	//시험문제 페이지 조회 ( 모든 시험 문제 조회 )
	public List<InstTestDomain> searchTest(InstTestViewDTO itvDTO) {
		List<InstTestDomain> list = null;
		try {
			list = iMapper.selectAllInstTest(itvDTO);
		}catch(PersistenceException pe) {
			pe.printStackTrace();
		}//end catch
		
		return list;
	}//searchTest
	
	//시험문제 - 하나 조회
		public InstTestDomain searchOneTest(InstTestViewDTO itvDTO) {
			InstTestDomain itDomain = null;
			
			return iDTO;
		}//searchOneTest
	
	//시험문제 - 삭제
	public int removeTest(String testId) {
		int testPage = 0;
		
		return testPage;
	}//removeTest
	
	//시험문제 - 수정
	public int modifyTest(InstTestDTO iDTO) {
		int testPage = 0;
		
		return testPage;
	}//modifyTest
	
	//시험문제 - 생성
	public boolean writeTest(InstTestDTO iDTO) {
		boolean flag = false;
		
		try {
			int cnt = iMapper.insertTest(iDTO);
			flag = cnt == 1;
		}catch(PersistenceException pe) {
			pe.printStackTrace();
		}
		return flag;
	}//writeTest
	
}//class
