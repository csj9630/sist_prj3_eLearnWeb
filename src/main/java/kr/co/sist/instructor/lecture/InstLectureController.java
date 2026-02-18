package kr.co.sist.instructor.lecture;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.user.lecture.board.LectureDomain;
import kr.co.sist.user.lecture.board.LectureRangeDTO;
import kr.co.sist.user.lecture.detail.LectureAllDetailDomain;

@RequestMapping("instructor")
@Controller
public class InstLectureController {
	@Autowired
	private InstLectureService ls;

	// 강의 썸네일 이미지 저장 경로
	@Value("${file.lecture.img-path}")
	private String imgPath;

	/**
	 * 강사용 강의 목록 + 검색어
	 * 
	 * @param rDTO
	 * @param model
	 * @return
	 */
	@GetMapping("/lectureList")
	public String InstlectureList(InstLectureSearchDTO sDTO, HttpSession session, Model model) {

		// String instId = (String)session.getAttribute("instId");
		String instId = "inst5";
		sDTO.setInstId(instId);

		List<InstLectureDomain> list = ls.searchInstLectureList(sDTO);

		// model에 할당하여 view로 전달.
		model.addAttribute("lectureList", list);

		model.addAttribute("sDTO",sDTO); // 검색어 유지를 위해 전달.
		model.addAttribute("imgPath", imgPath);

		System.out.println("==============================================");
		System.out.println(sDTO);
		System.out.println("==============================================");
		return "instructor/lecture/instLectureList";
	}// method

	/*	*//**
			 * 강의 상세 페이지 이동
			 * 
			 * @param lectId 강의 아이디
			 * @param model
			 * @return
			 *//*
				 * @GetMapping("/detail") public String lectureDetail(@RequestParam String
				 * lectId, Model model) { LectureAllDetailDomain allDetail =
				 * ls.getLectureAllDetail(lectId); model.addAttribute("allDetail", allDetail);
				 * model.addAttribute("imgPath", imgPath); return "user/lecture/lectureDetail";
				 * }// method
				 */

}// class
