package kr.co.sist.instructor.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/instructor/payment")
@Controller("instructorPaymentController")
public class PaymentController {

	@Autowired
	private PaymentService ps;
	
	// [수익 확인 페이지] 메인 진입점
	@GetMapping("/inst_payment") 
	public String searchMyPaySum(HttpSession session, Model model) {
		// [테스트용] 강사 로그인 (나중에 실제 로그인 연동 시 삭제)
		session.setAttribute("instId", "inst3");
		String instId = (String) session.getAttribute("instId");
		
		if(instId == null) {
			return "redirect:/"; // 로그인 안되어있으면 튕김
		}

		// 1. 총 매출액 조회
		int totalSum = ps.getMyPaySum(instId);
		
		// 2. 강의별 매출 리스트 조회
		List<PaymentSumDTO> list = ps.getMyPaySumByLect(instId);
		
		// 3. 총 수강생 수 조회
		int userCount = ps.getMyUserCount(instId);
		
		// 4. 모델에 저장
		model.addAttribute("totalSum", totalSum);
		model.addAttribute("list", list);
		model.addAttribute("userCount", userCount);
		
		// 5. 뷰 페이지 이동 (inst_payment.html)
		return "instructor/payment/inst_payment";
	}//searchMyPaySum
	
	// (필요 없다면 삭제하셔도 되는 메소드들)
	@GetMapping("/searchMyPaySumByLect")
	public String searchMyPaySumByLect(String instId, Model model) {
		return "";
	}
	
	@GetMapping("/searchMyUserCount")
	public String searchMyUserCount(String instId, Model model) {
		return "";
	}
	
}//class