package kr.co.sist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 개발용 링크 연결을 위한 파트.
 */
@Controller
public class DevController {

	@GetMapping("/dev")
	public String devIndex() {
		return "devIndex";
	}
}
