package kr.co.sist.admin.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/review")
public class AdminReviewController {

	@Autowired
	private AdminReviewService rs;

	@GetMapping("/reviewList")
	public String reviewList(AdminReviewRangeDTO rDTO, HttpSession session, Model model, HttpServletRequest req) {
		int totalCount = rs.totalCnt(rDTO); // 총 게시물의 수
		int pageScale = rs.pageScale(); // 한 화면에 보여줄 게시물의 수
		int totalPage = rs.totalPage(totalCount, pageScale); // 총 페이지 수
		int currentPage = rDTO.getCurrentPage(); // 현재 페이지
		int startNum = rs.startNum(currentPage, pageScale); // 시작 번호
		int endNum = rs.endNum(startNum, pageScale); // 끝 번호

		rDTO.setStartNum(startNum);
		rDTO.setEndNum(endNum);
		rDTO.setTotalPage(totalPage);
		rDTO.setUrl("admin/review/reviewList");
		
		System.out.println(rs);
		
		List<AdminReviewDomain> reviewList = rs.searchReviewList(rDTO); // 게시글 내용
		String pagination = rs.pagination(rDTO); // 페이지네이션
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("listNum", totalCount - (currentPage - 1) * pageScale);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("currentUri", req.getRequestURI());

		return "admin/review/reviewList";
	} // reviewList
	
	@GetMapping("/reviewDetail")
	public String reviewDetail(@RequestParam String reviewId, Model model) {
		AdminReviewDomain reviewDomain = rs.searchOneReview(reviewId);
		model.addAttribute("reviewDomain", reviewDomain);

		return "admin/review/reviewDetail";
	} // reviewDetail
	
	@PostMapping("/reviewRemoveProcess")
	public String removeReviewProcess(HttpSession session, AdminReviewDTO rDTO, Model model) {

		// 임시
		// session.setAttribute("user_id", "user2");
		// 임시
		// String userId = (String) session.getAttribute("user_id");
		String userId = (String) session.getAttribute("userId");
		rDTO.setUser_id(userId);

		boolean flag = rs.removeReview(rDTO);
		model.addAttribute("flag", flag);

		return "admin/review/reviewRemoveProcess";
	} // removeReviewProcess

	// 🌟 [추가됨] 모달에 띄울 리뷰 정보를 JSON 형태로 반환하는 AJAX 전용 메서드
	@ResponseBody
	@GetMapping("/reviewDetailAjax")
	public ResponseEntity<AdminReviewDomain> reviewDetailAjax(@RequestParam String reviewId) {

		// 기존에 구현되어 있는 서비스 로직(상세조회)을 재사용
		AdminReviewDomain reviewDomain = rs.searchOneReview(reviewId);

		// 화면(View)이 아닌 데이터(JSON)를 반환
		return ResponseEntity.ok(reviewDomain);
	}

} // class