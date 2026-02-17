package kr.co.sist.user.lecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("user/lecture")
@Controller
public class LectureController {
	@Autowired
	private LectureService ls;

	@GetMapping("/lectureList")
	public String lectureList(LectureRangeDTO rDTO, Model model) {
		
		//url 지정
		rDTO.setUrl("/user/lecture/lectureList");
		
		int totalCount = ls.totalCnt(rDTO); //전체 게시물의 수 조회
		int pageScale = ls.pageScale(); // 한 화면에 8개
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

	    //model에 할당하여 view로 전달.
	    model.addAttribute("lectureList", list);
	    model.addAttribute("pagination", pagination);
	    model.addAttribute("totalCount", totalCount);
	    
	    return "user/lecture/lecutureList";
	}// method
	
	

}// class
