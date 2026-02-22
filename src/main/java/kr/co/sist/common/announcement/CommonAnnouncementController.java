package kr.co.sist.common.announcement;

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

@RequestMapping("/common/announcement")
@Controller
public class CommonAnnouncementController {
	
	@Autowired
	private CommonAnnouncementService as;
	
	@GetMapping("/announcementList")
	public String announcementList(CommonAnnouncementRangeDTO rDTO, Model model, HttpServletRequest req) {
		int totalCount = as.totalCnt(rDTO);						// 총 게시물의 수
		int pageScale = as.pageScale();							// 한 화면에 보여줄 게시물의 수
		int totalPage = as.totalPage(totalCount, pageScale);	// 총 페이지 수
		int currentPage = rDTO.getCurrentPage();				// 현재 페이지
		int startNum = as.startNum(currentPage, pageScale); 	// 시작 번호
		int endNum = as.endNum(startNum, pageScale);			// 끝 번호
		
		rDTO.setStartNum(startNum);
		rDTO.setEndNum(endNum);
		rDTO.setTotalPage(totalPage);
		rDTO.setUrl("common/announcement/announcementList");
		
		System.out.println(as);
		
		List<CommonAnnouncementDomain> announcementList = as.searchAnnouncementList(rDTO); // 게시글 내용
		String pagination = as.pagination(rDTO); // 페이지네이션
		
		model.addAttribute("listNum", totalCount - (currentPage - 1)*pageScale);
		model.addAttribute("announcementList", announcementList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("currentUri", req.getRequestURI());
		
		return "common/announcement/announcementList";
	} // announcementList
	
	@GetMapping("/announcementDetail")
	public String announcementDetail(@RequestParam(defaultValue = "0") int annId, Model model, HttpServletRequest req) {
		CommonAnnouncementDomain announcementDomain =  as.searchOneAnnouncement(annId);
		model.addAttribute("announcementDomain", announcementDomain);
		model.addAttribute("currentUri", req.getRequestURI());
		
		return "common/announcement/announcementDetail";
	} // announcementDetail
	
} // class
