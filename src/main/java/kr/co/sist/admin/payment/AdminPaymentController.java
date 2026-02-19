package kr.co.sist.admin.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/payment")
public class AdminPaymentController {

	@Autowired
	private AdminPaymentService aps;
	
	@GetMapping("/searchPayPriceSum")
	public String searchPaySum(Model model, AdminPaymentSearchDTO apsDTO, HttpServletRequest req) { 
		int totalProfit=aps.getTotalProfit();
		int adminProfit=aps.getAdminProfit();
		List<String> instList=aps.getAllInst();
		List<LectProfitDomain> lectProfitList=aps.getLectProfit(apsDTO);
		
		model.addAttribute("instructor", instList);
		model.addAttribute("totalProfit", totalProfit);
		model.addAttribute("adminProfit", adminProfit);
		//하단 수익 데이터
		model.addAttribute("lectProfitList", lectProfitList);
		//상단 제목
		model.addAttribute("pageTitle", "수익 관리");
		model.addAttribute("currentUri", req.getRequestURI());
		
		return "admin/payment/payment";
	}//searchPaySum
	
	@GetMapping("/searchInstProfit")
	@ResponseBody
	public List<LectProfitDomain> searchInstProfit(Model model, AdminPaymentSearchDTO apsDTO) {
		System.out.println(aps.getLectProfit(apsDTO));
		return aps.getLectProfit(apsDTO);
	}//searchInstProfit
}
