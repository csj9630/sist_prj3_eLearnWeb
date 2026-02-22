package kr.co.sist.instructor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/instructor")
public class InstructorDashBoardController {

	@Autowired
	private InstructorDashBoardService idbs;
	
	//세션에서 강사 id 가져오는 작업 미진행
	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpSession session, HttpServletRequest req) {
		String instId=(String) session.getAttribute("instId");
		int lectCnt=idbs.getTotalCountLect(instId);
		int ScoreReview=idbs.getTotalScoreReview(instId);
		int userCnt=idbs.getTotalCountUser(instId);
		int instProfit=idbs.getTotalProfitInst(instId);
		
		model.addAttribute("lectCnt", lectCnt);
		model.addAttribute("ScoreReview", ScoreReview);
		model.addAttribute("userCnt", userCnt);
		model.addAttribute("instProfit", instProfit);
		model.addAttribute("currentUri", req.getRequestURI());
		
		model.addAttribute("pageTitle", "대시보드");
		
		return "instructor/dashboard/dashboard";
	}
}
