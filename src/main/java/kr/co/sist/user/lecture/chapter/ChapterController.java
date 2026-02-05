package kr.co.sist.user.lecture.chapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RequestMapping("/lecture/chapter")
//@Controller
@RestController
public class ChapterController {
	@Autowired
	private ChapterService cs;

//	@GetMapping("/")
//	public String youtubeTest(Model model) {
//
//		// model.addAttribute("video",video);
//
//		return "youtubeTest/youtubeTest";
//	}// method

//	@GetMapping("/viewList")
	@GetMapping("/")
	public String viewChapter(Model model) {

		List<ChapterDomain> list = cs.searchAllChapter();
		model.addAttribute("chapterList",list);

		return list.toString();
	}// method
	
	
}// class
