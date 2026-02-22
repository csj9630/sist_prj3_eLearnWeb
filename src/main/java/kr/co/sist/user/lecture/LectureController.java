package kr.co.sist.user.lecture;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.sist.user.lecture.board.LectureDomain;
import kr.co.sist.user.lecture.board.LectureRangeDTO;
import kr.co.sist.user.lecture.detail.LectureAllDetailDomain;
import kr.co.sist.user.payment.PaymentService;

@RequestMapping("user/lecture")
@Controller
public class LectureController {
	@Autowired
	private LectureService ls;

	   @Autowired
	    private PaymentService ps;
	//강의 썸네일 이미지 저장 경로
	@Value("${file.lecture.img-path}")
	private String imgPath;
	
	/**
	 * 강의 목록 + 페이지네이션
	 * @param rDTO
	 * @param model
	 * @return
	 */
	@GetMapping("/lectureList")
	public String lectureList( LectureRangeDTO rDTO, Model model) {
		int objOnPage = 8;//한 화면에 8개로 지정.
		
		//url 지정
		rDTO.setUrl("/user/lecture/lectureList");
		ls.setPageScale(objOnPage); 
		
		int totalCount = ls.totalCnt(rDTO); //전체 게시물의 수 조회
		int pageScale = ls.pageScale(); 
        int totalPage = ls.totalPage(totalCount); // 총 페이지 수
	    
        //페이지 번호
        int currentPage = rDTO.getCurrentPage(); //현재 페이지 번호
        int startNum = ls.startNum(currentPage); //시작 번호
        int endNum = ls.endNum(startNum); // 끝 번호

        //DTO에 계산한 번호 저장-> 시작/끝 번호, 전체페이지수
	    rDTO.setStartNum(startNum);
	    rDTO.setEndNum(endNum);
	    rDTO.setTotalPage(totalPage);

	    //DB에서 조회된 리스트 가져와기
	    List<LectureDomain> list = ls.getLectureList(rDTO);
	    
	    //페이지네이션 생성.
	    String pagination = ls.pagination2(rDTO); // 조립된 HTML

	    //검색용 필드 세팅.
	    Map<String, Object> filters = ls.getFilters();
	    
	    
	    //model에 할당하여 view로 전달.
	    model.addAttribute("lectureList", list);
	    model.addAttribute("pagination", pagination);
	    model.addAttribute("totalCount", totalCount);
	    model.addAllAttributes(filters);
	    model.addAttribute("imgPath", imgPath);
	    
	    return "user/lecture/lectureList";
	}// method
	
	/**
	 * 강의 상세 페이지 이동
	 * @param lectId 강의 아이디
	 * @param model
	 * @return
	 */
	@GetMapping("/detail")
	public String lectureDetail(@RequestParam String lectId, Model model) {
	    LectureAllDetailDomain allDetail = ls.getLectureAllDetail(lectId);
	    List<String> skillList = ls.getSkillList(lectId);
	    model.addAttribute("allDetail", allDetail);
	    model.addAttribute("skillList", skillList);
	    model.addAttribute("imgPath", imgPath);
	    return "user/lecture/lectureDetail";
	}// method
	

}// class
