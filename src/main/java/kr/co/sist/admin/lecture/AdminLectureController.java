package kr.co.sist.admin.lecture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

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
	public String searhAllLect(Model model, AdminLectureSearchDTO alsDTO, HttpServletRequest req) {
		int totalCount=als.countLectureByCategory(alsDTO); //강의 개수
		int totalPage=(int) Math.ceil((double)totalCount/alsDTO.getSize()); //페이지 수
		System.out.println(totalCount + "/" + totalPage);
		System.out.println(alsDTO.getSize() + "/" + alsDTO.getPage());
		//카테고리(게임 개발, 교양 등등) 출력
		List<String> category=als.searchAllCategory();
		//카테고리 적용 리스트
		List<AdminLectureDomain> lectureByCategory=als.searchLectureByCategory(alsDTO);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("size", alsDTO.getSize());
		model.addAttribute("page", alsDTO.getPage());
		model.addAttribute("category", alsDTO.getCategoryName());
		model.addAttribute("alsDTO", alsDTO);
		model.addAttribute("categoryList", category);
		model.addAttribute("lectByCategory", lectureByCategory);
		
		//헤더에 사용할 페이지명
		model.addAttribute("pageTitle", "교육 과목 관리");
		model.addAttribute("currentUri", req.getRequestURI());
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
	public Map<String, Object> searchCategoryLect(Model model, AdminLectureSearchDTO alsDTO) {
		int totalCount=als.countLectureByCategory(alsDTO); //강의 개수
		int totalPage=(int) Math.ceil((double)totalCount/alsDTO.getSize()); //페이지 수
		int size=alsDTO.getSize();
		int page=alsDTO.getPage();
		
	    List<AdminLectureDomain> list = als.searchLectureByCategory(alsDTO);

	    Map<String, Object> result=new HashMap<>();
	    result.put("size", size);
	    result.put("page", page);
	    result.put("totalCount", totalCount);
	    result.put("totalPage", totalPage);
	    result.put("list", list);
	    
	    return result;
	}
	 
	/**
	 * 강의 상태 변경 method
	 * @param lectureId
	 */
	@PostMapping("/updateLectState")
	@ResponseBody
	public Map<String, String> updateLectStop(@RequestParam String lectureId, @RequestParam String action) {
		Map<String, String> status=new HashMap<String, String>();
		
		//중지 버튼을 눌렀을 때
		if("stop".equals(action)) {
			System.out.println(als.disableLecture(lectureId));
			als.disableLecture(lectureId);
			status.put("result", "비공개");
		} else if("open".equals(action)) {
			System.out.println(als.ableLecture(lectureId));
			als.ableLecture(lectureId);
			status.put("result", "공개");
		}
	
		return status;
	}
	
	/**
	 * 강의 관리 화면 처리 method
	 * @param model
	 * @return
	 */
	@GetMapping("/searchNotApprLect")
	public String searchNotApprLect(Model model, AdminLectureSearchDTO alsDTO, HttpServletRequest req) {
		int totalCount=als.countNotApprLect(alsDTO); //강의 개수
		int totalPage=(int) Math.ceil((double)totalCount/alsDTO.getSize()); //페이지 수
		
		List<AdminNotApprLectureDomain> notApprLectureList=als.searchNotApprLectList(alsDTO);
		List<String> category=als.searchAllCategory();
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("size", alsDTO.getSize());
		model.addAttribute("page", alsDTO.getPage());
		model.addAttribute("alsDTO", alsDTO);
		model.addAttribute("notApprLectList", notApprLectureList);
		model.addAttribute("categoryList", category);
		//헤더에 사용할 페이지명
		model.addAttribute("pageTitle", "강의 관리");
		model.addAttribute("currentUri", req.getRequestURI());
		
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
	public Map<String, Object> searchNotApprCategoryLect(Model model, AdminLectureSearchDTO alsDTO) {
		int totalCount=als.countNotApprLect(alsDTO); //강의 개수
		int totalPage=(int) Math.ceil(totalCount/alsDTO.getSize()); //페이지 수
		int size=alsDTO.getSize();
		int page=alsDTO.getPage();
		
	    List<AdminNotApprLectureDomain> list = als.searchNotApprLectList(alsDTO);
	    
	    Map<String, Object> result=new HashMap<>();
	    result.put("size", size);
	    result.put("page", page);
	    result.put("totalCount", totalCount);
	    result.put("totalPage", totalPage);
	    result.put("list", list);
	    
	    return result;
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