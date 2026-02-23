package kr.co.sist.instructor.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/instructor/review")
public class InstructorReviewController {

	@Autowired
	private InstructorReviewService rs;

	@GetMapping("/reviewList")
	public String reviewList(InstructorReviewRangeDTO rDTO, HttpSession session, Model model, HttpServletRequest req) {
		String instId = (String)session.getAttribute("instId");
		rDTO.setInstId(instId);
		
		int totalCount = rs.totalCnt(rDTO); // 총 게시물의 수
		int pageScale = rs.pageScale(); // 한 화면에 보여줄 게시물의 수
		int totalPage = rs.totalPage(totalCount, pageScale); // 총 페이지 수
		int currentPage = rDTO.getCurrentPage(); // 현재 페이지
		int startNum = rs.startNum(currentPage, pageScale); // 시작 번호
		int endNum = rs.endNum(startNum, pageScale); // 끝 번호
		
		rDTO.setStartNum(startNum);
		rDTO.setEndNum(endNum);
		rDTO.setTotalPage(totalPage);
		rDTO.setUrl("instructor/review/reviewList");
		
		System.out.println(rs);
		
		List<InstructorReviewDomain> reviewList = rs.searchReviewList(rDTO); // 게시글 내용
		String pagination = rs.pagination(rDTO); // 페이지네이션
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("listNum", totalCount - (currentPage - 1) * pageScale);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("currentUri", req.getRequestURI());

		return "instructor/review/reviewList";
	} // reviewList
	
	@GetMapping("/reviewDetail")
	public String reviewDetail(@RequestParam String reviewId, Model model) {
		InstructorReviewDomain reviewDomain = rs.searchOneReview(reviewId);
		model.addAttribute("reviewDomain", reviewDomain);
		
		return "instructor/review/reviewDetail";
	} // reviewDetail
	
	@ResponseBody
	@GetMapping("/reviewDetailAjax")
	public ResponseEntity<InstructorReviewDomain> reviewDetailAjax(@RequestParam String reviewId) {

		// 기존에 구현되어 있는 서비스 로직(상세조회)을 재사용
		InstructorReviewDomain reviewDomain = rs.searchOneReview(reviewId);

		// 화면(View)이 아닌 데이터(JSON)를 반환
		return ResponseEntity.ok(reviewDomain);
	}

} // class