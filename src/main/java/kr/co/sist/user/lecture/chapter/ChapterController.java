package kr.co.sist.user.lecture.chapter;

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
import kr.co.sist.common.file.FileService;
import kr.co.sist.common.lecture.CommonLectureService;

@RequestMapping("/lecture/chapter")
@Controller
public class ChapterController {
	// *****************//
	// 임시 로그인 세션 정보는 CSJTempController에 있다.
	// *****************//

	@Autowired
	private ChapterService cs;

	@Autowired
	private CommonLectureService common;

	@Autowired
	private FileService fileService;

	@Value("${user.upload-doc-dir}") // application.properties에 설정된 경로 (예: C:/uploads/)
	private String uploadDocDir;

	/**
	 * 강의 수강한 학생의 수강 이력 포함된 챕터 리스트
	 * 
	 * @param lectId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String viewChapterProgress(@RequestParam String lectId, HttpSession session, Model model) {
		String userId = (String) session.getAttribute("userId");
		
		String lectName = cs.getLectureName(lectId);
		if(lectName == null || lectName =="") {
			model.addAttribute("msg", "[NULL-LECT]존재하지 않는 강의입니다!");
			
			return "common/err/err";
		}
		
		// 수강 챕터 목록 만들어서 리턴.
		ChapterDTO cdto = new ChapterDTO(userId, lectId);
		List<StuChapterDomain> list = cs.searchChapterProgress(cdto); // 수강 이력 리스트
		
		//조회 챕터가 없거나, 강의 자체가 없으면 무조건 시험 거짓 리턴.
		boolean isExamReady = false;
		if(!(list == null || list.isEmpty() ||lectName == null || lectName =="")) {
			isExamReady = cs.isExamReady(userId, lectId); // 시험 버튼 활성화 여부
		}//if
		
		Integer latestScore = cs.getLatestScore(userId, lectId); // 최신 시험 점수
		
		
		model.addAttribute("chapterProgress", list);
		model.addAttribute("lectId", lectId);
		model.addAttribute("lectName", lectName);
		model.addAttribute("isExamReady", isExamReady);
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
	public String getVideoList(@RequestParam(required = false, defaultValue = "1") String chptrId,
			@RequestParam String lectId, HttpSession session, Model model) {
		String userId = (String) session.getAttribute("userId");
		ChapterDTO cdto = new ChapterDTO(userId, lectId);
		List<VideoDomain> vdList = cs.getVideoInfoList(cdto);
		String lectName = cs.getLectureName(lectId);

		// 출석 체크. 실패 시 에러메시지 전달.
		try {
			cs.checkAttendance(userId);
		} catch (RuntimeException e) {
			model.addAttribute("errorMsg", e.getMessage());
		} // end catch

		model.addAttribute("vdList", vdList); // 영상 정보 리스트을 전송.
		model.addAttribute("lectId", lectId);
		model.addAttribute("lectName", lectName);
		model.addAttribute("startChptrId", chptrId); // 처음 재생할 챕터ID 전송.

		return "user/lecture/chapter/watchVideo2";
	}// method

	@PostMapping("/saveRecord")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> saveRecord(@RequestBody VideoDTO vdto) {
		Map<String, Object> result = new HashMap<>();


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
	 * Ajax - 파일 다운로드 전 파일 존재 여부 체크.
	 * 
	 * @param chptrId
	 * @return
	 */
	@GetMapping("/checkFile")
	@ResponseBody // JSON 데이터를 반환하기 위해 필요
	public Map<String, Boolean> checkFile(@RequestParam("chptrId") String chptrId) {
		Map<String, Boolean> response = new HashMap<>();
		try {
			FileDomain fileDomain = cs.getFileInfo(chptrId);
			String fileName = fileDomain.getDoc();

			// 파일 서비스에서 파일 존재 체크.
			boolean exists = fileService.checkFileExists(fileName, uploadDocDir);

			// 파일이 존재하고 읽기 가능한지 확인
			response.put("exists", exists);
		} catch (Exception e) {
			response.put("exists", false);
		}
		return response;
		
	}//method

	/**
	 * 파일 다운로드, 기본적으로 위의 파일 존재 체크를 먼저 하고 실행한다.
	 * 
	 * @param chptrId
	 * @return
	 */
	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile(@RequestParam("chptrId") String chptrId) {
		// 요청 결과는 기본 실패
		ResponseEntity<Resource> result = ResponseEntity.internalServerError().build();

		try {
			// DB 파일명을 조회
			FileDomain fileDomain = cs.getFileInfo(chptrId);

			// 조회된 게 없으면 null을 직접 넣어서 에러 없게 하기..
			String fileName = (fileDomain != null) ? fileDomain.getDoc() : null;

			// 파일 서비스(파일명,파일경로)로 다운로드하고 결과 저장
			result = fileService.downloadFile(fileDomain.getDoc(), uploadDocDir);

		} catch (Exception e) {
			e.printStackTrace();
		} // catch

		return result;
	}// method

}// class
