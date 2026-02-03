package kr.co.sist.user.my.payrec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.user.payment.PayDetailDTO; // Mapper가 반환하는 DTO 사용
import kr.co.sist.user.payment.UserPaymentMapper; // 이미 만들어둔 Mapper 재사용

@Service
public class PayRecService {
	
	@Autowired
	private UserPaymentMapper userPaymentMapper; // DB 연결용 Mapper 주입
	
	// 구매 내역 조회
	public List<PayDetailDTO> searchPurchaseLectures(String userId) {
		List<PayDetailDTO> list = null;
		
		// Mapper를 통해 DB에서 조회
		list = userPaymentMapper.selectMyPurchaseList(userId);
		
		return list;
	}//searchPurchaseLectures
	
}//class