package kr.co.sist.user.lecture;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

/**
 * 개발용 임시 컨트롤러. 배포 전 제거할 것.
 */
@Controller
public class CSJTempController {
	
	@GetMapping("/csj")
	public String viewTempIndex(HttpSession session) {
		String tempUserId = "user1";
		session.setAttribute("userId",tempUserId );
		return "user/lecture/indexTemp";
	}// method
}
