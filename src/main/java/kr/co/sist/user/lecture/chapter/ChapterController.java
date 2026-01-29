package kr.co.sist.instructor.lecture.chapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class ChapterController {
	@Autowired(required = false)
	private ChapterService cs;

	@GetMapping("/")
	public String youtubeTest(Model model) {

		// model.addAttribute("video",video);

		return "/youtubeTest/youtubeAPISample";
	}// method

}// class
