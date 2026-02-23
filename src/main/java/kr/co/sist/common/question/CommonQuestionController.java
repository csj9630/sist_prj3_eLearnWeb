package kr.co.sist.common.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/common/question")
@Controller
public class CommonQuestionController {
	
	@Autowired
	private CommonQuestionService qs;
	
	@GetMapping("/questionList")
	public String QuestionList(CommonQuestionRangeDTO rDTO, Model model, HttpServletRequest req) {
		int totalCount = qs.totalCnt(rDTO);						// 총 게시물의 수
		int pageScale = qs.pageScale();							// 한 화면에 보여줄 게시물의 수
		int totalPage = qs.totalPage(totalCount, pageScale);	// 총 페이지 수
		int currentPage = rDTO.getCurrentPage();				// 현재 페이지
		int startNum = qs.startNum(currentPage, pageScale); 	// 시작 번호
		int endNum = qs.endNum(startNum, pageScale);			// 끝 번호
		
		rDTO.setStartNum(startNum);
		rDTO.setEndNum(endNum);
		rDTO.setTotalPage(totalPage);
		rDTO.setUrl("common/question/questionList");
		
		System.out.println(qs);
		
		List<CommonQuestionDomain> QuestionList = qs.searchQuestionList(rDTO); // 게시글 내용
		String pagination = qs.pagination(rDTO); // 페이지네이션
		
		model.addAttribute("listNum", totalCount - (currentPage - 1)*pageScale);
		model.addAttribute("questionList", QuestionList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("currentUri", req.getRequestURI());
		
		return "common/question/questionList";
	} // QuestionList
	
	@GetMapping("/questionDetail")
	public String QuestionDetail(@RequestParam(defaultValue = "0") String qId, Model model, HttpServletRequest req) {
		CommonQuestionDomain QuestionDomain =  qs.searchOneQuestion(qId);
		model.addAttribute("questionDomain", QuestionDomain);
		model.addAttribute("currentUri", req.getRequestURI());
		
		return "common/question/questionDetail";
	} // QuestionDetail
	
} // class
