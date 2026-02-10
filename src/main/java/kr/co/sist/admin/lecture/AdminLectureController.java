package kr.co.sist.admin.lecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/lecture")
public class AdminLectureController {

	@Autowired
	private AdminLectureService als;
	
	/**
	 * 교육 과목 화면 처리 method
	 * @param model
	 * @param alsDTO
	 * @return
	 */
	@GetMapping("/searchAllLect")
	public String searhAllLect(Model model, AdminLectureSearchDTO alsDTO) {
		//카테고리(게임 개발, 교양 등등) 출력
		List<String> category=als.searchAllCategory();
		//카테고리 적용 리스트
		List<AdminLectureDomain> lectureByCategory=als.searchLectureByCategory(alsDTO);
		
		model.addAttribute("alsDTO", alsDTO);
		model.addAttribute("categoryList", category);
		model.addAttribute("lectByCategory", lectureByCategory);
		
		//헤더에 사용할 페이지명
		model.addAttribute("pageTitle", "교육 과목 관리");
		
		return "admin/lecture/searchAllLect";
	}
	
	/**
	 * 교육 과목 관리-카테고리 변경 ajax
	 * @param model
	 * @param alsDTO
	 * @return
	 */
	@GetMapping("/searchCategoryLect")
	@ResponseBody 
	public List<AdminLectureDomain> searchCategoryLect(Model model, AdminLectureSearchDTO alsDTO) {
		System.out.println(als.searchLectureByCategory(alsDTO));
		return als.searchLectureByCategory(alsDTO); 
	}
	 
	/**
	 * 강의 상태 변경 method
	 * @param lectureId
	 */
	@PostMapping("/updateLectState")
	@ResponseBody
	public void updateLectState(String lectureId) {
		als.disableLecture(lectureId);
	}
	
	/**
	 * 강의 관리 화면 처리 method
	 * @param model
	 * @return
	 */
	@GetMapping("/searchNotApprLect")
	public String searchNotApprLect(Model model, AdminLectureSearchDTO alsDTO) {
		List<AdminNotApprLectureDomain> notApprLectureList=als.searchNotApprLectList(alsDTO);
		List<String> category=als.searchAllCategory();
		
		model.addAttribute("alsDTO", alsDTO);
		model.addAttribute("notApprLectList", notApprLectureList);
		model.addAttribute("categoryList", category);
		//헤더에 사용할 페이지명
		model.addAttribute("pageTitle", "강의 관리");
		
		return "admin/lecture/searchNotApprLect";
	}
	
	/**
	 * 강의 관리-카테고리 변경 ajax
	 * @param model
	 * @param alsDTO
	 * @return
	 */
	@GetMapping("/searchNotApprCategoryLect")
	@ResponseBody 
	public List<AdminNotApprLectureDomain> searchNotApprCategoryLect(Model model, AdminLectureSearchDTO alsDTO) {
		return als.searchNotApprLectList(alsDTO); 
	}
	
	/**
	 * 강의관리 상세 페이지
	 * @param model
	 * @param lectureId
	 * @return
	 */
	@GetMapping("/searchDetailNotApprLect")
	public String searchDetailNotApprLect(Model model, @RequestParam String lectureId) {
		List<AdminLectureDetailDomain> lectureDetail=als.searchLectureDetail(lectureId);
		List<AdminLectureChapterDomain> lectureChapter=als.searchLectureChapter(lectureId);
		
		model.addAttribute("lecture", lectureDetail);
		model.addAttribute("chapter", lectureChapter);
		
		return "admin/lecture/searchDetailNotApprLect";
	}
	
	@PostMapping("/approve")
	@ResponseBody
	public void approve(String lectureId) {
		als.approvalLecture(lectureId);
	}
	
}