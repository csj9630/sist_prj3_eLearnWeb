package kr.co.sist.user.lecture.review;

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

@RequestMapping("/lecture/review")
@Controller
public class UserReviewController {
	
	@Autowired
	private UserReviewService rs;
	
	@GetMapping("/reviewList")
	public String reviewList(UserReviewRangeDTO rDTO, Model model) {
		int totalCount = rs.totalCnt(rDTO);						// 총 게시물의 수
		int pageScale = rs.pageScale();							// 한 화면에 보여줄 게시물의 수
		int totalPage = rs.totalPage(totalCount, pageScale);	// 총 페이지 수
		int currentPage = rDTO.getCurrentPage();				// 현재 페이지
		int startNum = rs.startNum(currentPage, pageScale); 	// 시작 번호
		int endNum = rs.endNum(startNum, pageScale);			// 끝 번호
		
		rDTO.setStartNum(startNum);
		rDTO.setEndNum(endNum);
		rDTO.setTotalPage(totalPage);
		rDTO.setUrl("/user/lecture/review/reviewList");
		
		System.out.println(rs);
		
		List<UserReviewDomain> reviewList = rs.searchReviewList(rDTO); // 게시글 내용
		String pagination = rs.pagination2(rDTO); // 페이지네이션
		
		model.addAttribute("listNum", totalCount - (currentPage - 1)*pageScale);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("pagination", pagination);
		
		return "/user/lecture/review/reviewList";
	} // reviewList
	
	@GetMapping("/reviewWriteFrm")
	public String writeForm(HttpSession session, UserReviewDTO rDTO, HttpServletRequest request, Model model) {
		model.addAttribute("ip", request.getRemoteAddr());
		
		// 임시
		session.setAttribute("user_id", "user2");
		String userId = (String) session.getAttribute("user_id");
		rDTO.setUser_id(userId);
		// 임시
		
		return "/user/lecture/review/reviewWriteFrm";
	} // writeForm
	
	@PostMapping("/reviewWriteFrmProcess")
	public String writeFormProcess(HttpSession session, UserReviewDTO rDTO, HttpServletRequest request, Model model) {
		rDTO.setRegip(request.getRemoteAddr());
		
		// 임시
//		session.setAttribute("user_id", "user2");
		String userId = (String) session.getAttribute("user_id");
		rDTO.setUser_id(userId);
		// 임시
		
		boolean flag = rs.addReview(rDTO);
		String resultMsg = "작성 실패";
		if(flag) {
			resultMsg = "작성 성공";
		} // end if
		model.addAttribute("msg", resultMsg);
		model.addAttribute("flag", flag);
		
		return "/user/lecture/review/reviewWriteFrmProcess";
	} // writeFormProcess
	
	@GetMapping("/reviewDetail")
	public String reviewDetail(@RequestParam String reviewId, Model model) {
		UserReviewDomain reviewDomain =  rs.searchOneReview(reviewId);
		model.addAttribute("reviewDomain", reviewDomain);
		
		return "/user/lecture/review/reviewDetail";
	} // reviewDetail
	
	@PostMapping("/modifyReviewProcess")
	public String modifyReviewProcess(HttpSession session, UserReviewDTO rDTO, HttpServletRequest request, Model model) {
		rDTO.setRegip(request.getRemoteAddr());
		
		// 임시
		session.setAttribute("user_id", "user1");
		String userId = (String) session.getAttribute("user_id");
		rDTO.setUser_id(userId);
		// 임시
		
		boolean flag = rs.modifyReview(rDTO);
		model.addAttribute("flag", flag);
		
		return "/user/lecture/review/modifyReviewProcess";
	} // modifyReviewProcess
	
	@PostMapping("/removeReviewProcess")
	public String removeReviewProcess(HttpSession session, UserReviewDTO rDTO, Model model) {
		
		// 임시
		session.setAttribute("user_id", "user1");
		String userId = (String) session.getAttribute("user_id");
		rDTO.setUser_id(userId);
		// 임시
		
		boolean flag = rs.removeReview(rDTO);
		model.addAttribute("flag", flag);
		
		return "/user/lecture/review/removeReviewProcess";
	} // removeReviewProcess
	
} // clrss
