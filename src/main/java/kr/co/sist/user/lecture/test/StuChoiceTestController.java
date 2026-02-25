package kr.co.sist.user.lecture.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user/lecture/test")
@Controller
public class StuChoiceTestController {

	@Autowired
	private StuTestService sts;
	
		// 시험 선택 Frm 띄워주기(시험 시작 or 지난 시험 및 해설)
		@GetMapping("/choiceTestFrm")
		public String instTestFrm(Model model, String lectId, HttpSession session) {
			
			String testId = sts.searchTestId(lectId);
			String userId = (String) session.getAttribute("userId");
			
			
			model.addAttribute("testId", testId);
			model.addAttribute("userId", userId);
			model.addAttribute("lectId", lectId);
			
			return "user/lecture/test/userTestChoiceFrm";
		}// instTestFrm
		
}
