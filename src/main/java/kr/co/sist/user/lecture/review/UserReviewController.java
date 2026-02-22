package kr.co.sist.user.lecture.review;

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
@RequestMapping("/lecture/review")
public class UserReviewController {

	@Autowired
	private UserReviewService rs;

	@GetMapping("/reviewList")
	public String reviewList(UserReviewRangeDTO rDTO, HttpSession session, Model model) {
		int totalCount = rs.totalCnt(rDTO); // 총 게시물의 수
		int pageScale = rs.pageScale(); // 한 화면에 보여줄 게시물의 수
		int totalPage = rs.totalPage(totalCount, pageScale); // 총 페이지 수
		int currentPage = rDTO.getCurrentPage(); // 현재 페이지
		int startNum = rs.startNum(currentPage, pageScale); // 시작 번호
		int endNum = rs.endNum(startNum, pageScale); // 끝 번호
		double avgScore = rs.avgScore(rDTO);

		rDTO.setStartNum(startNum);
		rDTO.setEndNum(endNum);
		rDTO.setTotalPage(totalPage);
		rDTO.setUrl("/user/lecture/review/reviewList");

		System.out.println(rs);

		List<UserReviewDomain> reviewList = rs.searchReviewList(rDTO); // 게시글 내용
		String pagination = rs.pagination2(rDTO); // 페이지네이션

		// 🌟 [추가된 로직] 로그인한 사용자 ID 가져오기 (임시 세션 기준)
		// session.setAttribute("user_id", "user2"); // (임시 코드)
		// String userId = (String) session.getAttribute("user_id");
		String userId = (String) session.getAttribute("userId");

		// 🌟 [추가된 로직] 내 리뷰 조회
		UserReviewDTO myReq = new UserReviewDTO();
		myReq.setLect_id(rDTO.getLectId());
		myReq.setUser_id(userId);
		UserReviewDomain myReview = rs.searchMyReview(myReq);

		// 🌟 [추가된 로직] Model에 내 리뷰 정보와 현재 접속자 ID 저장
		model.addAttribute("myReview", myReview);
		model.addAttribute("sessionUserId", userId);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("avgScore", avgScore);
		model.addAttribute("listNum", totalCount - (currentPage - 1) * pageScale);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("pagination", pagination);

		return "/user/lecture/review/reviewList";
	} // reviewList

	@GetMapping("/reviewWriteFrm")
	public String writeForm(HttpSession session, UserReviewDTO rDTO, HttpServletRequest request, Model model) {
		model.addAttribute("ip", request.getRemoteAddr());

		// 임시
		// session.setAttribute("user_id", "user2");
		// String userId = (String) session.getAttribute("user_id");
		String userId = (String) session.getAttribute("userId");
		rDTO.setUser_id(userId);
		// 임시

		return "/user/lecture/review/reviewWriteFrm";
	} // writeForm

	@PostMapping("/reviewWriteFrmProcess")
	public String writeFormProcess(HttpSession session, UserReviewDTO rDTO, HttpServletRequest request, Model model) {
		rDTO.setRegip(request.getRemoteAddr());

		// 임시
		// session.setAttribute("user_id", "user2");
		// 임시
		// String userId = (String) session.getAttribute("user_id");
		String userId = (String) session.getAttribute("userId");
		rDTO.setUser_id(userId);

		boolean flag = rs.addReview(rDTO);
		String resultMsg = "리뷰 작성을 실패하였습니다.";
		if (flag) {
			resultMsg = "리뷰가 작성되었습니다.";
		} // end if
		model.addAttribute("msg", resultMsg);
		model.addAttribute("flag", flag);

		return "/user/lecture/review/reviewWriteFrmProcess";
	} // writeFormProcess

	@GetMapping("/reviewDetail")
	public String reviewDetail(@RequestParam String reviewId, Model model) {
		UserReviewDomain reviewDomain = rs.searchOneReview(reviewId);
		model.addAttribute("reviewDomain", reviewDomain);

		return "/user/lecture/review/reviewDetail";
	} // reviewDetail

	@PostMapping("/reviewModifyProcess")
	public String modifyReviewProcess(HttpSession session, UserReviewDTO rDTO, HttpServletRequest request,
			Model model) {
		rDTO.setRegip(request.getRemoteAddr());

		// 임시
		// session.setAttribute("user_id", "user2");
		// 임시
		// String userId = (String) session.getAttribute("user_id");
		String userId = (String) session.getAttribute("userId");
		rDTO.setUser_id(userId);

		boolean flag = rs.modifyReview(rDTO);
		model.addAttribute("flag", flag);

		return "/user/lecture/review/reviewModifyProcess";
	} // modifyReviewProcess

	@PostMapping("/reviewRemoveProcess")
	public String removeReviewProcess(HttpSession session, UserReviewDTO rDTO, Model model) {

		// 임시
		// session.setAttribute("user_id", "user2");
		// 임시
		// String userId = (String) session.getAttribute("user_id");
		String userId = (String) session.getAttribute("userId");
		rDTO.setUser_id(userId);

		boolean flag = rs.removeReview(rDTO);
		model.addAttribute("flag", flag);

		return "/user/lecture/review/reviewRemoveProcess";
	} // removeReviewProcess

	// 🌟 [추가됨] 모달에 띄울 리뷰 정보를 JSON 형태로 반환하는 AJAX 전용 메서드
	@ResponseBody
	@GetMapping("/reviewDetailAjax")
	public ResponseEntity<UserReviewDomain> reviewDetailAjax(@RequestParam String reviewId) {

		// 기존에 구현되어 있는 서비스 로직(상세조회)을 재사용
		UserReviewDomain reviewDomain = rs.searchOneReview(reviewId);

		// 화면(View)이 아닌 데이터(JSON)를 반환
		return ResponseEntity.ok(reviewDomain);
	}

} // class