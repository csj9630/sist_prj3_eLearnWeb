package kr.co.sist.user.my.payrec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.user.payment.PayDetailDTO; 
import kr.co.sist.user.payment.UserPaymentMapper; 

@Service
public class PayRecService {
	
	@Autowired
	private UserPaymentMapper userPaymentMapper;
	
	// 구매 내역 조회
	public List<PayDetailDTO> searchPurchaseLectures(String userId) {
		List<PayDetailDTO> list = null;
		
		// Mapper를 통해 DB에서 조회
		list = userPaymentMapper.selectMyPurchaseList(userId);
		
		return list;
	}//searchPurchaseLectures
	
}//class