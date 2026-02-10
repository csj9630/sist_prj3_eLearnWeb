package kr.co.sist.user.lecture.test;

import java.util.List;
import kr.co.sist.instructor.lecture.test.InstTestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user/lecture/test")
@Controller
public class StuTestController {



	@Autowired
	private StuTestService ss;


	// 시험 페이지 Frm
	@GetMapping("/stuTestFrm")
	public String instTestFrm(Model model, String lectId, HttpSession session) {
		List<StuTestDomain> stdList = ss.searchTest(lectId);
		
		model.addAttribute("stdList",stdList);
		
		return "/user/lecture/test/userTestFrm";
	}// instTestFrm


	// 시험문제 제출 처리
	@PostMapping("/submitTestProcess")
	public String submitTestProcess(Model model, StuTestDTO stDTO, StuAnswerDTO saDTO, HttpSession session) {

		return "/user/lecture/test/testResultFrm";
	}// instTestFrm

	// 시험 이력, 점수 조회
	@GetMapping("/stuScore")
	public String stuScore(Model model, int lectId, HttpSession session) {

		return "/user/lecture/test/stuTestFrm";
	}// stuScore

	// 시험 답안 조회
	@GetMapping("/stuAnswer")
	public String stuAnswer(Model model, int test_id, String stu_id, HttpSession session) {

		return "/user/lecture/test/stuTestFrm";
	}// stuAnswer

}
