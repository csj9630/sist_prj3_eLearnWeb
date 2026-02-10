package kr.co.sist.user.lecture;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CSJTempController {
	
	@GetMapping("/csj")
	public String viewTempIndex() {
		return "user/lecture/indexTemp";
	}// method
}
