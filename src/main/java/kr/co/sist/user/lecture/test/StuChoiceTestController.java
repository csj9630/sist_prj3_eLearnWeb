package kr.co.sist.user.lecture.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user/lecture/test")
@Controller
public class StuChoiceTestController {

		// 시험 선택 Frm 띄워주기(시험 시작 or 지난 시험 및 해설)
		@GetMapping("/choiceTestFrm")
		public String instTestFrm(Model model, String lectId, String instId , HttpSession session) {
			
			return "user/lecture/test/userTestChoiceFrm";
		}// instTestFrm
}
