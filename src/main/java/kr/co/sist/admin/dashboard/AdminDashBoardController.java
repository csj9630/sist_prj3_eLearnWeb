package kr.co.sist.admin.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminDashBoardController {

	@Autowired
	private AdminDashBoardService adbs;
	
	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpSession session) {
		int instCnt=adbs.getTotalCountInst();
		int lectCnt=adbs.getTotalCountLect();
		int userCnt=adbs.getTotalCountUser();
		int totalProfit=adbs.getTotalProfit();
		List<AdminDashBoardDomain> payList=adbs.selectTopPayLecture();
		List<AdminDashBoardDomain> userList=adbs.selectMostUserLecture();
		
		//상단 4개의 데이터 값
		model.addAttribute("instCnt", instCnt);
		model.addAttribute("lectCnt", lectCnt);
		model.addAttribute("userCnt", userCnt);
		model.addAttribute("totalProfit", totalProfit);
		
		//최고 수익 강의 데이터
		model.addAttribute("payList", payList);
		//최다 수강 과목
		model.addAttribute("userList", userList);
		System.out.println(userList);
		//헤더에 쓸 페이지명
		model.addAttribute("pageTitle", "대시보드");
		
		return "admin/dashboard/dashboard";
	}
}
