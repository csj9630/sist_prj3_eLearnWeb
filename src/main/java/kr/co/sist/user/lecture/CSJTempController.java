package kr.co.sist.user.lecture;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 개발용 임시 컨트롤러. 배포 전 제거할 것.
 */
@Controller
public class CSJTempController {
	
	@GetMapping("/csj")
	public String viewTempIndex() {
		return "user/lecture/indexTemp";
	}// method
}
