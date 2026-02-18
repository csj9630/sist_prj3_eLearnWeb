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

    @Autowired
    private PaymentService ps;
    
    // 내 수익 조회 페이지(일단 링크 2개로 매핑함)
    @GetMapping({"/searchMyPay","/inst_payment"}) 
    public String searchMyPay(
            @RequestParam(value = "searchMonth", required = false) String searchMonth,
            HttpSession session, Model model) {
        
        String instId = (String) session.getAttribute("instId");
        if(instId == null) { instId = "inst1"; session.setAttribute("instId", instId); }

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
