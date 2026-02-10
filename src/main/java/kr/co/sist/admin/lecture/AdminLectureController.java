package kr.co.sist.admin.lecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/lecture")
public class AdminLectureController {

	@Autowired
	private AdminLectureService als;
	
	/**
	 * 교육 과목 화면 처리 method
	 * @param model
	 * @param mlsDTO
	 * @return
	 */
	@GetMapping("/searchAllLect")
	public String searhAllLect(Model model, ManageLectureSearchDTO mlsDTO) {
		List<String> category=als.searchAllCategory();
		
		//카테고리 적용 리스트
		List<ManageLectureDomain> lectureByCategory=als.searchLectureByCategory(mlsDTO);
		
		model.addAttribute("mlsDTO", mlsDTO);
		model.addAttribute("categoryList", category);
		model.addAttribute("lectByCategory", lectureByCategory);
		
		//헤더에 사용할 페이지명
		model.addAttribute("pageTitle", "교육 과목 관리");
		
		return "admin/lecture/searchAllLect";
	}
	
	/**
	 * 강의 상태 변경 method
	 * @param lectureId
	 */
	@GetMapping("/updateLectState")
	@ResponseBody
	public void updateLectState(String lectureId) {
		als.disableLecture(lectureId);
	}
	
	
	/**
	 * 강의 관리 처리 method
	 * @param model
	 * @return
	 */
	@GetMapping("/searchNotApprLect")
	public String searchNotApprLect(Model model) {
		List<ManageNotApprLectureDomain> notApprLectureList=als.searchNotApprLectList();
		model.addAttribute("notApprLectList", notApprLectureList);
		
		//헤더에 사용할 페이지명
		model.addAttribute("pageTitle", "강의 관리");
		
		return "admin/lecture/searchNotApprLect";
	}
}
