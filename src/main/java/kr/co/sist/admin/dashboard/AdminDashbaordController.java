package kr.co.sist.admin.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminDashbaordController {

	@Autowired
	private AdminDashBoardService adbs;
	
	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpSession session) {
		int instCnt=adbs.getTotalCountInst();
		int lectCnt=adbs.getTotalCountLect();
		int userCnt=adbs.getTotalCountUser();
		int totalProfit=adbs.getTotalProfit();
		
		//상단 4개의 데이터 값
		model.addAttribute("instCnt", instCnt);
		model.addAttribute("lectCnt", lectCnt);
		model.addAttribute("userCnt", userCnt);
		model.addAttribute("totalProfit", totalProfit);
		
		//헤더에 쓸 페이지명
		model.addAttribute("pageTitle", "대시보드");
		
		return "admin/dashboard/dashboard";
	}
}
