package kr.co.sist.instructor.dashboard;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorDashBoardService {

	@Autowired(required=false) 
	private InstructorDashBoardMapper idbm;
	
	public int getTotalCountLect(String instId) throws PersistenceException {
		int countLect=idbm.selectCountLect(instId);
		return countLect;
	}//getTotalCountInst
	
	public int getTotalScoreReview(String instId) throws PersistenceException {
		int score=idbm.selectScoreReview(instId);
		return score;
	}//getTotalCountInst
	
	public int getTotalCountUser(String instId) throws PersistenceException {
		int countUser=idbm.selectCountUser(instId);
		return countUser;
	}//getTotalCountInst

	public int getTotalProfitInst(String instId) throws PersistenceException {
		int profitInst=idbm.selectInstProfit(instId);
		return profitInst;
	}//getTotalCountInst
}