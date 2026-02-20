package kr.co.sist.instructor.lecture.chapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/instructor/chapter")
public class InstChapterController {

    @Autowired
    private InstChapterService chapterService;

    /**
     * 챕터 관리 페이지 이동
     * @par	m lectId 강의 ID
     */
    @GetMapping("/chapterManage")
    public String chapterManagePage(@RequestParam String lectId, Model model) {
        // 페이지 진입 시 해당 강의의 챕터 리스트를 미리 넘겨줄 수도 있습니다.
        List<InstChapterDTO> list = chapterService.searchChapterList(lectId);
        model.addAttribute("chapterList", list);
        model.addAttribute("lectId", lectId);
        
        return "instructor/lecture/chapterManage"; // 만들어둔 HTML 경로
    }

    
}