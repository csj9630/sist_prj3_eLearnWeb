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
	public String viewMain(Model model) {
		List<LectureDomain> list = ls.searchChapterProgress();
		model.addAttribute("lectureList", list);

		return "user/lecture/lecutureList";
	}// method
	
	

}// class
