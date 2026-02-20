package kr.co.sist.instructor.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/instructor/payment")
@Controller("instructorPaymentController")
public class PaymentController {

<<<<<<< HEAD
	@Autowired
	private PaymentService ps;
	
	// [수익 확인 페이지] 메인 진입점
	@GetMapping("/inst_payment") 
	public String searchMyPaySum(HttpSession session, Model model) {
        // 1. 세션에서 강사 ID 가져오기
        String instId = (String) session.getAttribute("instId");
        
        // 2. 로그인 안 된 상태면 테스트용 'inst1'로 자동 설정 (드롭다운 테스트용)
        if(instId == null) {
            instId = "inst1"; 
            session.setAttribute("instId", instId);
        }
=======
    @Autowired
    private PaymentService ps;
    
    // 내 수익 조회 페이지(일단 링크 2개로 매핑함)
    @GetMapping({"/searchMyPay","/inst_payment"}) 
    public String searchMyPay(
            @RequestParam(value = "searchMonth", required = false) String searchMonth,
            HttpSession session, Model model) {
        
        String instId = (String) session.getAttribute("instId");
        if(instId == null) { instId = "inst1"; session.setAttribute("instId", instId); }
>>>>>>> refs/heads/dev0220-2

<<<<<<< HEAD
        // 3. 총 매출액 조회
        int totalSum = ps.getMyPaySum(instId);
        
        // 4. 강의별 매출 리스트 조회
        List<PaymentSumDTO> list = ps.getMyPaySumByLect(instId);
        
        // 5. 총 수강생 수 조회
        int userCount = ps.getMyUserCount(instId);
        
        // 6. 모델에 저장
        model.addAttribute("totalSum", totalSum);
        model.addAttribute("list", list);
        model.addAttribute("userCount", userCount);
        
        return "instructor/payment/inst_payment";
    }

	
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
=======
        // 날짜 기본값 설정
        if(searchMonth == null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
            searchMonth = sdf.format(new java.util.Date());
        }//end if

        // 서비스 호출
        int monthRevenue = ps.getMonthPaySum(instId, searchMonth); // 월별 수익
        int totalRevenue = ps.getTotalPaySum(instId); // 누적 수익
        List<PaymentSumDTO> list = ps.getMyPaySumByLect(instId);  // 리스트
        
        // 모델 저장
        model.addAttribute("monthRevenue", monthRevenue);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("list", list);
        model.addAttribute("searchMonth", searchMonth); //드롭다운용 현재 날짜
        
        return "instructor/payment/inst_payment";
    }//searchMyPay
    
}//class		
>>>>>>> refs/heads/dev0220-2
