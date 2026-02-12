package kr.co.sist.user.lecture.chapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@GetMapping("/videoV1")
	public String watchVideo(@RequestParam String chptrId,  Model model) {
		
		VideoDomain0 vd = cs.getVideoInfo("user1", chptrId);
		
		
		model.addAttribute("vd", vd);
		
		return "user/lecture/chapter/watchVideo" ;
	}// method
	
	@GetMapping("/videoV2")
	public String getVideoList( @RequestParam(required = false, defaultValue = "1") int num, @RequestParam String lectId, @RequestParam String stuId,  Model model) {
		//stuId = "user1"; //나중에 Session에서 받아올 것.
		
		ChapterDTO cdto = new ChapterDTO(stuId, lectId);
		List<VideoDomain> vdList  = cs.getVideoInfoList(cdto);
		
		model.addAttribute("vdList", vdList);
		model.addAttribute("startNum", num); // 처음 재생할 번호
		
		return "user/lecture/chapter/watchVideo2" ;
	}// method

	@PostMapping("/saveRecord")
	@ResponseBody
    public ResponseEntity<Map<String, Object>> saveRecord(@RequestBody VideoDTO vdto) {
        Map<String, Object> result = new HashMap<>();
        System.out.println("----받은 데이터------");
        System.out.println(vdto);
       // vdto.recalculate();
        //System.out.println("----변환 데이터------");
        //System.out.println(vdto);
        
        // 서비스 호출
        boolean isSaved = cs.saveVideoRecord(vdto);
        
        if (isSaved) {
            result.put("status", "success");
            result.put("message", "시청 기록이 저장되었습니다.");
            return ResponseEntity.ok(result);
        } else {
            result.put("status", "fail");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
	}// method
	
}// class
