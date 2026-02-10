package kr.co.sist.admin.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPaymentController {

	@Autowired
	private AdminPaymentService aps;
	
	@GetMapping("/payment/searchPayPriceSum")
	public String searchPaySum(Model model) { 
		List<LectProfitDomain> lectProfitList=aps.getLectProfit();
		//하단 수익 데이터
		model.addAttribute("lectProfitList", lectProfitList);
		
		return "admin/payment/payment";
	}//searchPaySum
}
