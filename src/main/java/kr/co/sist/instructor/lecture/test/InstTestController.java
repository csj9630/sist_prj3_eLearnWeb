package kr.co.sist.instructor.lecture.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/instructor/lecture/test")
@Controller
public class InstTestController {
	
	@Autowired
	private InstTestService is;
	
	// 시험문제 페이지 보기 ( 모든 시험 문제 조회 )
	@PostMapping("/instTestFrm")
	public String instTestFrm(Model model, InstTestViewDTO itvDTO, HttpSession session) {
		List<InstTestDomain> list = is.searchTest(itvDTO);
		
		model.addAttribute("testList", list);
		
		return "/instructor/lecture/test/instTestFrm";
	}//testFrm

	//시험문제 하나 조회
	@GetMapping("/instTestOneFrm")
	@ResponseBody
	public String instTestOneFrm(Model model, InstTestViewDTO itvDTO, HttpSession session) {
		return is.searchOneTest(itvDTO);
	}//instTestOneFrm
	
	// 시험문제 생성
	@PostMapping("/createTest")
	public String createTest(Model model, InstTestDTO iDTO) {
		boolean flag = is.writeTest(iDTO);
		model.addAttribute("result", flag);
		
		return "/instructor/lecture/test/instTestResult";
	}//createTest
	
	// 시험문제 수정
	@PostMapping("/modifyTest")
	public String modifyTest(Model model, InstTestDTO iDTO, HttpSession session) {
		
		return "";
	}//modifyTest
	
	// 시험문제 삭제
	@PostMapping("/removeTest")
	public String removeTest(Model model, InstTestViewDTO itvDTO, HttpSession session) {
		System.out.println("삭제 메소드");
		return is.removeTest(itvDTO);
	}//modifyTest
	
	
}
