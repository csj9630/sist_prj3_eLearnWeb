package kr.co.sist.instructor.lecture.test;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TempController {

	@RequestMapping(value="/leeTest",method= {GET,POST})
	public String main(Model model) {
		
		return "/myIndex";
	}
	
	
}
