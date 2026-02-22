package kr.co.sist.instructor.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    
  //강의 썸네일 이미지 저장 경로
  	@Value("${file.lecture.img-path}")
  	private String imgPath;
    
    // 내 수익 조회 페이지(일단 링크 2개로 매핑함)
//    @GetMapping({"/inst_payment"}) 
//    public String searchMyPay(
//            @RequestParam(value = "searchMonth", required = false) String searchMonth,
//            HttpSession session, Model model) {
//        
//        String instId = (String) session.getAttribute("instId");
////        if(instId == null) { instId = "inst1"; session.setAttribute("instId", instId); }
//
//        //임시 테스트 코드
//        System.out.println("====== 현재 세션에 들어있는 instId 값은? : " + instId + " ======");
//        
//        // 날짜 기본값 설정
//        if(searchMonth == null) {
//            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
//            searchMonth = sdf.format(new java.util.Date());
//        }//end if
//
//        // 서비스 호출
//        int monthRevenue = ps.getMonthPaySum(instId, searchMonth); // 월별 수익
//        int totalRevenue = ps.getTotalPaySum(instId); // 누적 수익
//        List<PaymentSumDTO> list = ps.getMyPaySumByLect(instId);  // 리스트
//        
//        // 모델 저장
//        model.addAttribute("monthRevenue", monthRevenue);
//        model.addAttribute("totalRevenue", totalRevenue);
//        model.addAttribute("list", list);
//        model.addAttribute("searchMonth", searchMonth); //드롭다운용 현재 날짜
//        model.addAttribute("imgPath", imgPath);
//        
//        return "instructor/payment/inst_payment";
//    }//searchMyPay
    
  	@GetMapping({"/inst_payment"}) 
  	public String searchMyPay(
  	        @RequestParam(value = "searchMonth", required = false) String searchMonth,
  	        HttpSession session, Model model) {
  	    
  	    String instId = (String) session.getAttribute("instId");
//  	        if(instId == null) { instId = "inst1"; session.setAttribute("instId", instId); }

  	    // --- 👇 여기서부터 새로 교체한 테스트 코드입니다 👇 ---
  	    System.out.println("====== 현재 세션 주머니 속 모든 데이터 확인 ======");
  	    java.util.Enumeration<String> names = session.getAttributeNames();
  	    while(names.hasMoreElements()) {
  	        String name = names.nextElement();
  	        System.out.println("이름표(Key): " + name + " / 내용물(Value): " + session.getAttribute(name));
  	    }
  	    System.out.println("=============================================");
  	    // --- 👆 여기까지 👆 ---
  	    
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
  	    model.addAttribute("imgPath", imgPath);
  	    
  	    return "instructor/payment/inst_payment";
  	}//searchMyPay
  	
}//class		
