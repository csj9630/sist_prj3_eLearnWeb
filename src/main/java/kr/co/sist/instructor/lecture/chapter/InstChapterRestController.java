package kr.co.sist.instructor.lecture.chapter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/instructor/chapter/rest")
public class InstChapterRestController {

    @Autowired
    private InstChapterService chapterService;

    /**
     * 챕터 리스트 조회 (Ajax용)
     */
    @GetMapping("/chapterList")
    public List<InstChapterDTO> getChapterList(@RequestParam String lectId) {
        return chapterService.searchChapterList(lectId);
    }

    /**
     * 챕터 상세 조회
     */
    @GetMapping("/chapterDetail")
    public InstChapterDTO getChapterDetail(@RequestParam String chptrId) {
        return chapterService.searchChapterDetail(chptrId);
    }

    /**
     * 챕터 등록 (파일 업로드 포함)
     */
    @PostMapping("/addChapter")
    public String addChapter(InstChapterDTO icDTO, 
                             @RequestParam(value = "uploadFile", required = false) MultipartFile mf) {
        try {
            boolean success = chapterService.addChapter(icDTO, mf);
            return success ? "success" : "fail";
        } catch (IOException e) {
            e.printStackTrace();
            return "error: " + e.getMessage();
        }
    }

    /**
     * 챕터 수정 (파일 업로드 포함)
     */
    @PostMapping("/modifyChapter")
    public String modifyChapter(InstChapterDTO icDTO, 
                                @RequestParam(value = "uploadFile", required = false) MultipartFile mf) {
        try {
            boolean success = chapterService.modifyChapter(icDTO, mf);
            return success ? "success" : "fail";
        } catch (IOException e) {
            e.printStackTrace();
            return "error: " + e.getMessage();
        }
    }

    /**
     * 챕터 삭제
     */
    @PostMapping("/removeChapter")
    public String removeChapter(@RequestParam String chptrId) {
        boolean success = chapterService.removeChapter(chptrId);
        return success ? "success" : "fail";
    }
}