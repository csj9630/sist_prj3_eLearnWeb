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
	public String searchMyPurchase(HttpSession session, Model model) { // [중요] 세션 사용
		// 세션에서 아이디 가져오기
		String userId = (String) session.getAttribute("userId");
		
		if(userId == null) {
			return "redirect:/"; // 비로그인 시 메인으로
		}
		
		List<PayDetailDTO> list = prs.searchPurchaseLectures(userId);
		model.addAttribute("purchaseList", list);
		
		// [핵심] 파일이 실제로 있는 경로를 적어줍니다.
		return "user/my/payrec/purchase_list"; 
	}
}