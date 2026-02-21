package kr.co.sist.admin.announcement;

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

@RequestMapping("/admin/announcement")
@Controller
public class AdminAnnouncementController {
	
	@Autowired
	private AdminAnnouncementService as;
	
	@GetMapping("/announcementList")
	public String announcementList(AdminAnnouncementRangeDTO rDTO, Model model) {
		int totalCount = as.totalCnt(rDTO);						// 총 게시물의 수
		int pageScale = as.pageScale();							// 한 화면에 보여줄 게시물의 수
		int totalPage = as.totalPage(totalCount, pageScale);	// 총 페이지 수
		int currentPage = rDTO.getCurrentPage();				// 현재 페이지
		int startNum = as.startNum(currentPage, pageScale); 	// 시작 번호
		int endNum = as.endNum(startNum, pageScale);			// 끝 번호
		
		rDTO.setStartNum(startNum);
		rDTO.setEndNum(endNum);
		rDTO.setTotalPage(totalPage);
		rDTO.setUrl("admin/announcement/announcementList");
		
		System.out.println(as);
		
		List<AdminAnnouncementDomain> announcementList = as.searchAnnouncementList(rDTO); // 게시글 내용
		String pagination = as.pagination(rDTO); // 페이지네이션
		
		model.addAttribute("listNum", totalCount - (currentPage - 1)*pageScale);
		model.addAttribute("announcementList", announcementList);
		model.addAttribute("pagination", pagination);
		
		return "admin/announcement/announcementList";
	} // announcementList
	
	@GetMapping("/writeFrm")
	public String writeForm(HttpServletRequest request, Model model) {
		model.addAttribute("ip", request.getRemoteAddr());
		
		return "admin/announcement/writeFrm";
	} // writeForm
	
	@PostMapping("/announcementWriteFrmProcess")
	public String writeFormProcess(HttpSession session, AdminAnnouncementDTO aDTO, HttpServletRequest request, Model model) {
		aDTO.setRegip(request.getRemoteAddr());
		
		// 임시
		session.setAttribute("adm_id", "admin1");
		String admId = (String) session.getAttribute("adm_id");
		aDTO.setAdm_id(admId);
		// 임시
		
		boolean flag = as.addAnnouncement(aDTO);
		String resultMsg = "작성 실패";
		if(flag) {
			resultMsg = "작성 성공";
		} // end if
		model.addAttribute("msg", resultMsg);
		model.addAttribute("flag", flag);
		
		return "admin/announcement/announcementWriteFrmProcess";
	} // writeFormProcess
	
	@GetMapping("/announcementDetail")
	public String announcementDetail(@RequestParam(defaultValue = "0") int annId, Model model) {
		AdminAnnouncementDomain announcementDomain =  as.searchOneAnnouncement(annId);
		model.addAttribute("announcementDomain", announcementDomain);

		
		return "admin/announcement/announcementDetail";
	} // announcementDetail
	
	@PostMapping("/modifyAnnouncementProcess")
	public String modifyAnnouncementProcess(HttpSession session, AdminAnnouncementDTO aDTO, HttpServletRequest request, Model model) {
		aDTO.setRegip(request.getRemoteAddr());
		
		// 임시
		session.setAttribute("adm_id", "admin1");
		String admId = (String) session.getAttribute("adm_id");
		aDTO.setAdm_id(admId);
		// 임시
		
		boolean flag = as.modifyAnnouncement(aDTO);
		model.addAttribute("flag", flag);
		
		return "admin/announcement/announcementModifyProcess";
	} // modifyAnnouncementProcess
	
	@PostMapping("removeAnnouncementProcess")
	public String removeAnnouncementProcess(HttpSession session, AdminAnnouncementDTO aDTO, Model model) {
		
		// 임시
		session.setAttribute("adm_id", "admin1");
		// 임시
		String admId = (String) session.getAttribute("adm_id");
		aDTO.setAdm_id(admId);
		
		boolean flag = as.removeAnnouncement(aDTO);
		model.addAttribute("flag", flag);
		
		return "admin/announcement/announcementRemoveProcess";
	} // removeAnnouncementProcess
	
} // class
