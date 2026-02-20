package kr.co.sist.user.lecture.chapter;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.util.UriUtils;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/lecture/chapter")
@Controller
public class ChapterController {
	//*****************//
	//임시 로그인 세션 정보는 CSJTempController에 있다.
	//*****************//

	@Autowired
	private ChapterService cs;
	
	@Value("${user.upload-doc-dir}") // application.properties에 설정된 경로 (예: C:/uploads/)
    private String uploadDocDir;

	@GetMapping("/viewList")
	public String viewChapterList(Model model) {
		String lectId = "L1";
		List<ChapterDomain> list = cs.searchChapterList(lectId);

		model.addAttribute("chapterList", list);

		return "user/lecture/chapter/chapterList";
	}// method

	/**
	 * 강의 수강한 학생의 수강 이력 포함된 챕터 리스트
	 * @param lectId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/viewProgressList")
	public String viewChapterProgress( @RequestParam String lectId, HttpSession session,Model model) {
		String userId = (String) session.getAttribute("userId");
		ChapterDTO cdto = new ChapterDTO(userId, lectId);
		List<StuChapterDomain> list = cs.searchChapterProgress(cdto); //수강 이력 리스트
		boolean isExamReady = cs.isExamReady(list); //시험 버튼 활성화 여부
		Integer latestScore = cs.getLatestScore(userId, lectId); // 최신 시험 점수
		

		model.addAttribute("chapterProgress", list);
		model.addAttribute("lectId", lectId);
		model.addAttribute("isExamReady", isExamReady);
//		model.addAttribute("isExamReady", true);
		model.addAttribute("examScore", latestScore);

		
		return "user/lecture/chapter/chapterProgressList";
	}// method

	
	

	/**
	 * 강의 영상에 보여줄 lecture 별 chapter video 리스트.
	 * 
	 * @param num    chapter 번호
	 * @param lectId 강의id
	 * @param userId 학생id -> session
	 * @param model
	 * @return video dto list
	 */
	@GetMapping("/video")
	public String getVideoList(@RequestParam(required = false, defaultValue = "1") String chptrId, @RequestParam String lectId,
			HttpSession session, Model model) {
		String userId = (String) session.getAttribute("userId");
		ChapterDTO cdto = new ChapterDTO(userId, lectId);
		List<VideoDomain> vdList = cs.getVideoInfoList(cdto);

		// 출석 체크. 실패 시 에러메시지 전달.
		try {
			cs.checkAttendance(userId);
		} catch (RuntimeException e) {
			model.addAttribute("errorMsg", e.getMessage());
		} // end catch
		


		model.addAttribute("vdList", vdList); // 영상 정보 리스트을 전송.
		model.addAttribute("startChptrId", chptrId); // 처음 재생할 번호 전송.

		return "user/lecture/chapter/watchVideo2";
	}// method

	@PostMapping("/saveRecord")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> saveRecord(@RequestBody VideoDTO vdto) {
		Map<String, Object> result = new HashMap<>();
		System.out.println("----받은 데이터------");
		System.out.println(vdto);
		// vdto.recalculate();
		// System.out.println("----변환 데이터------");
		// System.out.println(vdto);

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
	
	
	
	/**
	 * 파일 다운로드 전 파일 존재 여부 체크.
	 * @param chptrId
	 * @return
	 */
	@GetMapping("/checkFile")
	@ResponseBody // JSON 데이터를 반환하기 위해 필요
	public Map<String, Boolean> checkFile(@RequestParam("chptrId") String chptrId) {
	    Map<String, Boolean> response = new HashMap<>();
	    try {
	        FileDomain fileDomain = cs.getFileInfo(chptrId);
	        Path filePath = Paths.get(uploadDocDir).resolve(fileDomain.getDoc()).normalize();
	        File file = filePath.toFile();
	        
	        // 파일이 존재하고 읽기 가능한지 확인
	        response.put("exists", file.exists() && file.isFile());
	    } catch (Exception e) {
	        response.put("exists", false);
	    }
	    return response;
	}
	
	/**
	 * 파일 다운로드
	 * @param chptrId
	 * @return
	 */
	@GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("chptrId") String chptrId) {
        try {
            // 1. Service를 통해 DB 조회
            FileDomain fileDomain = cs.getFileInfo(chptrId);
            String fileName = fileDomain.getDoc();

            // 2. 물리적 경로 설정
            Path filePath = Paths.get(uploadDocDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // 3. 파일명 인코딩 및 헤더 설정
            String encodedFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
            
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .body(resource);

        } catch (RuntimeException e) {
            // Service에서 던진 예외 처리
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}// class
