package kr.co.sist.user.my.payrec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.user.payment.PayDetailDTO;

@RequestMapping("/user/my/payrec")
@Controller
public class PayRecController {
    
    @Autowired
    private PayRecService prs;
    
    @GetMapping("/searchMyPurchase")
    public String searchMyPurchase(HttpSession session, Model model) {
        // 1. 세션에서 아이디 가져오기
        String userId = (String) session.getAttribute("userId");
        
        // 2. 없으면 테스트 계정(user4) 자동 설정
        if(userId == null) {
            userId = "user4";
            session.setAttribute("userId", userId);
        }
        
        // 3. 서비스 호출
        List<PayDetailDTO> list = prs.searchPurchaseLectures(userId);
        model.addAttribute("purchaseList", list);
        
        return "user/my/payrec/purchase_list"; 
    }
}