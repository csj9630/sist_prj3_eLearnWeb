package kr.co.sist.admin.payment;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.common.util.CryptoUtil;

@Service
public class AdminPaymentService {

	@Autowired(required=false)
	private AdminPaymentMapper apm;
	
	@Autowired
	private CryptoUtil cryptoUtil;
	
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
		List<LectProfitDomain> lectProfitList = apm.selectLectProfit(apsDTO);
		
		if (lectProfitList != null) {
			for (LectProfitDomain lectProfitDomain : lectProfitList) {
				lectProfitDomain.setInst_name(cryptoUtil.decryptSafe(lectProfitDomain.getInst_name()));
			}
		}
		
		return lectProfitList;
	}//getLectProfit
}
