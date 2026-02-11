package kr.co.sist.user.lecture.chapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DB 실행 체크용
 */
@RequestMapping("/csj")
@RestController
public class TempRest_ChapterController {
	@Autowired
	private ChapterService cs;

//	@GetMapping("/")
//	public String youtubeTest(Model model) {
//
//		// model.addAttribute("video",video);
//
//		return "youtubeTest/youtubeTest";
//	}// method

	@GetMapping("/tempviewList")
	// @GetMapping("/")
	public List<VideoDomain2> viewChapter(Model model) {
		String lectId = "L1";
		String userId="user1";
		ChapterDTO cdto = new ChapterDTO(userId, lectId);
		List<VideoDomain2> list = cs.getVideoInfoList(cdto);
		model.addAttribute("list", list);

		return list;
	}// method

	

}// class
