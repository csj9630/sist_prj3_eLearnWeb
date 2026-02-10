package kr.co.sist.user.lecture.chapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/lecture/chapter")
@Controller
public class ChapterController {
	@Autowired
	private ChapterService cs;

	
	@GetMapping("/viewList")
	public String viewChapterList(Model model) {
		String lectId = "L1";
		List<ChapterDomain> list = cs.searchChapterList(lectId);

		model.addAttribute("chapterList", list);
		
		return "user/lecture/chapter/chapterList" ;
	}// method
	
	@GetMapping("/viewProgressList")
	public String viewChapterProgress(Model model) {
		ChapterDTO cdto = new ChapterDTO("user1", "L1");
		List<StuChapterDomain> list = cs.searchChapterProgress(cdto);
		
		//영상시간 변환테스트.
		/* for (ChapterDomain cd : list) { cd.setLength(1204); } */
		
		model.addAttribute("chapterProgress", list);
		
		return "user/lecture/chapter/chapterProgressList" ;
	}// method
	
	@GetMapping("/video")
	public String watchVideo(@RequestParam String chptrId,  Model model) {
		
		VideoDomain vd = cs.getVideoInfo("user1", chptrId);
		
		model.addAttribute("vd", vd);
		
		return "user/lecture/chapter/watchVideo" ;
	}// method

}// class
