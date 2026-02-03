package kr.co.sist.admin.dashboard;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDashBoardService {

	@Autowired(required=false) 
	private AdminDashBoardMapper adbm;
	
	public int getTotalCountInst() throws PersistenceException {
		int count=adbm.selectCountInst();
		
		return count;
	}//getTotalCountInst

	public int getTotalCountLect() throws PersistenceException {
		int count=adbm.selectCountLect();
		
		return count;
	}//getTotalCountLect
	
	public int getTotalCountUser() throws PersistenceException {
		int count=adbm.selectCountUser();
		
		return count;
	}//getTotalCountUser
	
	public int getTotalProfit() throws PersistenceException {
		int count=adbm.selectAllProfit();
		
		return count;
	}//getTotalProfit
	
	public List<LectProfitDomain> getLectProfit() throws PersistenceException {
		List<LectProfitDomain> lectProfitList;
		
		lectProfitList=adbm.selectLectProfit();
		return lectProfitList;
	}//getLectProfit
}