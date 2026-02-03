package kr.co.sist.admin.announcement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/announcement")
public class AdminAnnouncementController {

	@GetMapping("/searchAllAnn")
	public String listAnnouncement(Model model) {
		
		return "/admin/announcement/searchAllAnn";
	}
}
