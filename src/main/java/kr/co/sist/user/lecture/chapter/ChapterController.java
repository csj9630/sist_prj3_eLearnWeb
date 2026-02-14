package kr.co.sist.user.lecture.chapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co.sist.common.member.CommonMemberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/lecture/chapter")
@Controller
public class ChapterController {
	//*****************//
	//임시 로그인 세션 정보는 CSJTempController에 있다.
	//*****************//

	@Autowired
	private ChapterService cs;

	@GetMapping("/viewList")
	public String viewChapterList(Model model) {
		String lectId = "L1";
		List<ChapterDomain> list = cs.searchChapterList(lectId);

		model.addAttribute("chapterList", list);

		return "user/lecture/chapter/chapterList";
	}// method

	@GetMapping("/viewProgressList")
	public String viewChapterProgress(Model model) {
		ChapterDTO cdto = new ChapterDTO("user1", "L1");
		List<StuChapterDomain> list = cs.searchChapterProgress(cdto);

		// 영상시간 변환테스트.
		/* for (ChapterDomain cd : list) { cd.setLength(1204); } */

		model.addAttribute("chapterProgress", list);

		return "user/lecture/chapter/chapterProgressList";
	}// method

	/**
	 * Youtube API 테스트용.
	 * 
	 * @param chptrId
	 * @param model
	 * @return
	 */
//	@GetMapping("/videoV1")
//	public String watchVideo(@RequestParam String chptrId,  Model model) {
//		
//		String userId = "user1";
//		VideoDomain0 vd = cs.getVideoInfo(userId, chptrId);
//		
//		
//		model.addAttribute("vd", vd);
//		
//		return "user/lecture/chapter/watchVideo" ;
//	}// method

	/**
	 * 강의 영상에 보여줄 lecture 별 chapter video 리스트.
	 * 
	 * @param num    chapter 번호
	 * @param lectId 강의id
	 * @param userId 학생id -> session
	 * @param model
	 * @return video dto list
	 */
	@GetMapping("/video")
	public String getVideoList(@RequestParam(required = false, defaultValue = "1") int num, @RequestParam String lectId,
			HttpSession session, Model model) {
		String userId = (String) session.getAttribute("userId");
		ChapterDTO cdto = new ChapterDTO(userId, lectId);
		List<VideoDomain> vdList = cs.getVideoInfoList(cdto);

		// 출석 체크. 실패 시 에러메시지 전달.
		try {
			cs.checkAttendance(userId);
		} catch (RuntimeException e) {
			model.addAttribute("errorMsg", e.getMessage());
		} // end catch

		System.out.println("=======session 사용자 id =======" + userId);

		model.addAttribute("vdList", vdList); // 영상 정보 리스트을 전송.
		model.addAttribute("startNum", num); // 처음 재생할 번호 전송.

		return "user/lecture/chapter/watchVideo2";
	}// method

	@PostMapping("/saveRecord")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> saveRecord(@RequestBody VideoDTO vdto) {
		Map<String, Object> result = new HashMap<>();
		System.out.println("----받은 데이터------");
		System.out.println(vdto);
		// vdto.recalculate();
		// System.out.println("----변환 데이터------");
		// System.out.println(vdto);

		// 서비스 호출
		boolean isSaved = cs.saveVideoRecord(vdto);

		if (isSaved) {
			result.put("status", "success");
			result.put("message", "시청 기록이 저장되었습니다.");
			return ResponseEntity.ok(result);
		} else {
			result.put("status", "fail");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}// method

}// class
