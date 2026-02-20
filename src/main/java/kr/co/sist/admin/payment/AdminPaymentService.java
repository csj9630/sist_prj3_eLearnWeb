package kr.co.sist.admin.payment;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminPaymentService {

	@Autowired(required=false)
	private AdminPaymentMapper apm;
	
	public List<String> getAllInst() throws PersistenceException {
		return apm.selectAllInst();
	}
	
	public int getTotalProfit() throws PersistenceException {
		return apm.selectAllProfit();
	}//getTotalProfit
	
	public int getAdminProfit() throws PersistenceException {
		return apm.selectAdminProfit();
	}
	
	public List<LectProfitDomain> getLectProfit(AdminPaymentSearchDTO apsDTO) throws PersistenceException {
		List<LectProfitDomain> lectProfitList;
		
		lectProfitList=apm.selectLectProfit(apsDTO);
		return lectProfitList;
	}//getLectProfit
}