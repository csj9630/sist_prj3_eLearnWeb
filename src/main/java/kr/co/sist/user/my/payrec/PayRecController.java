package kr.co.sist.user.my.payrec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    
    @Value("${file.lecture.img-path}")
  	private String imgPath;
    
    /**
     * value에 ""(빈 문자열)을 추가하여 기본 경로 접속도 허용
     * /user/my/payrec || /user/my/payrec/searchMyPurchase (둘 다 가능)
     */
    @GetMapping({"", "/searchMyPurchase"})
    public String searchMyPurchase(HttpSession session, Model model) {
        //세션에서 아이디 가져옴.
        String userId = (String) session.getAttribute("userId");
        
        // userId가 없다면 user1으로 설정
        if(userId == null) {
            userId = "user1";
            session.setAttribute("userId", userId);
        }
        
        // 서비스 호출 (기존 메소드 그대로 사용)
        List<PayDetailDTO> list = prs.searchPurchaseLectures(userId);
        model.addAttribute("purchaseList", list);
        model.addAttribute("imgPath", imgPath);
        
        return "user/my/payrec/purchase_list"; 
    }//searchMyPurchase

}//class