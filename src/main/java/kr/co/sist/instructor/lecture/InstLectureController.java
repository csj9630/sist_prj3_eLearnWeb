package kr.co.sist.instructor.lecture;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.sist.common.lecture.CommonLectureService;
import kr.co.sist.user.lecture.LectureService;

@RequestMapping("instructor/lecture")
@Controller
public class InstLectureController {
	@Autowired
	private InstLectureService ls;

	@Autowired
	private CommonLectureService commonService; // 공통 서비스 주입

	// 강의 썸네일 출력용 이미지 상대 경로
	@Value("${file.lecture.img-path}")
	private String imgPath;

	// 강의 썸네일 이미지 파일 저장 경로
	@Value("${file.lecture.upload-path}")
	private String uploadImgPath;

	/**
	 * 강사용 강의 목록 + 검색어
	 * 
	 * @param rDTO
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String InstlectureList(InstLectureSearchDTO sDTO, HttpSession session, Model model) {

		// String instId = (String)session.getAttribute("instId");
		String instId = "inst5";
		sDTO.setInstId(instId);

		List<InstLectureDomain> list = ls.searchInstLectureList(sDTO);

		// model에 할당하여 view로 전달.
		model.addAttribute("lectureList", list);

		model.addAttribute("sDTO", sDTO); // 검색어 유지를 위해 전달.
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

	// 등록/수정 폼 이동 (lectId 유무로 판단)
	@GetMapping("/manage")
	public String lectureForm(@RequestParam(required = false) String lectId, Model model, HttpSession session) {
		if (lectId != null && !lectId.isEmpty()) {
			// 수정 모드: 기존 데이터 조회 (서비스에 상세조회 메서드 필요)
			// InstLectureDTO detail = ls.getLectureDetail(lectId);
			// model.addAttribute("detail", detail);
			model.addAttribute("isUpdate", true);
		} else {
			// 등록 모드
			model.addAttribute("isUpdate", false);
		}

		// 카테고리 목록 조회
		model.addAttribute("categoryList", commonService.getAllCategories());
		model.addAttribute("existingSkills", commonService.getAllSkills());

		return "instructor/lecture/lectureForm";
	}

	//  실제 저장 처리 (Insert)
	@PostMapping("/save")
	public String saveLecture(InstLectureDTO ldto, @RequestParam("thumbFile") MultipartFile thumbFile,
			HttpServletRequest request, HttpSession session) {

		// 1. 기본 정보 설정
		String instId = "inst5"; // 실제로는 session.getAttribute("instId") 사용
		ldto.setInstId(instId);
		ldto.setRegip(request.getRemoteAddr());

		// 기본값 설정 (null 에러 방지용)
		ldto.setThumbnail("default.png");

		// 2. 썸네일 파일 업로드 처리
	    String newThumbnail = ls.uploadThumbnail(thumbFile);
		ldto.setThumbnail(newThumbnail);
	
		boolean isSuccess = false;
		// 3. 서비스 호출 
		if (ldto.getLectId() == null || ldto.getLectId().isEmpty()) {
			isSuccess = ls.addLecture(ldto);
		} else {
			// ls.modifyLecture(ldto); // 서비스에 수정 메서드 추가 필요
		}

		return "redirect:/instructor/lecture/list";
	}// method

	//강의 활성화 전환.
	@PostMapping("/updateAvailability")
	@ResponseBody // AJAX 요청이므로 데이터만 응답
	public String updateAvailability(@RequestParam("lectId") String lectId,
			@RequestParam("availability") int availability) {

		boolean result = ls.modifyAvailability(lectId, availability);

		return result ? "success" : "fail";
	}// method
	
	@GetMapping("/detail")
	public String lectureDetail(@RequestParam("lectId") String lectId, Model model) {
		InstLectureDTO ldto = ls.getLectureDetail(lectId);
	    
	    model.addAttribute("ldto", ldto);
	    model.addAttribute("isDetail", true); // 상세 보기용 플래그
	    
	    // 폼 구성에 필요한 전체 스킬 목록도 가져오기 (기존 서비스 메서드 활용)
	    model.addAttribute("categoryList", commonService.getAllCategories());
	    model.addAttribute("existingSkills", commonService.getAllSkills());
		model.addAttribute("imgPath", imgPath);
	    return "instructor/lecture/lectureForm"; // 기존 등록 폼 재사용
	}
	
	// 수정 폼 열기
	@GetMapping("/edit")
	public String editForm(@RequestParam String lectId, Model model) {
	    InstLectureDTO ldto = ls.getLectureDetail(lectId);
	    
	    model.addAttribute("ldto", ldto);
	    model.addAttribute("isDetail", false); // 수정 모드이므로 false
	    model.addAttribute("categoryList", commonService.getAllCategories());
	    model.addAttribute("existingSkills", commonService.getAllSkills());
	    
	    return "instructor/lecture/lectureForm";
	}
	
	//수정 처리하기
	@PostMapping("/modify")
	public String modifyLecture(InstLectureDTO ldto, @RequestParam("thumbFile") MultipartFile thumbFile, HttpServletRequest request) {
	    
	    // 1. 신규 파일이 업로드되었다면 처리
	    String newThumbnail = ls.uploadThumbnail(thumbFile);
	    if (newThumbnail != null) {
	        ldto.setThumbnail(newThumbnail); // 신규 파일명으로 교체
	    }
	    
	    ldto.setRegip(request.getRemoteAddr());
	    
	    // 2. 서비스 호출
	    ls.modifyLecture(ldto);
	    
	    return "redirect:/instructor/lecture/detail?lectId=" + ldto.getLectId();
	}


}// class
