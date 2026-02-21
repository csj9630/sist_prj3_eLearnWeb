package kr.co.sist.admin.lecture;

import java.io.IOException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.sist.common.file.FileService;
import kr.co.sist.user.lecture.chapter.ChapterService;
import kr.co.sist.user.lecture.chapter.FileDomain;

@Controller
@RequestMapping("/admin/lecture")
public class AdminLectureController {

	@Autowired
	private AdminLectureService als;
	
	@Autowired
	private ChapterService cs ;

    // 프로필 이미지 파일 저장 경로 (출력만 할 때는 당장 안 쓰지만 세팅 유지)
    @Value("${file.lecture.img-path}")
    private String profileUploadPath;
	
    // 파일 업로드 위치
    @Value("${user.upload-doc-dir}")
    private String uploadDocDir;
    
	/**
	 * 교육 과목 화면 처리 method
	 * @param model
	 * @param alsDTO
	 * @return
	 */
	@GetMapping("/searchAllLect")
	public String searhAllLect(Model model, AdminLectureSearchDTO alsDTO, HttpServletRequest req) {
		int totalCount=als.countLectureByCategory(alsDTO); //강의 개수
		int totalPage=(int) Math.ceil((double)totalCount/alsDTO.getSize()); //페이지 수
		System.out.println(totalCount + "/" + totalPage);
		System.out.println(alsDTO.getSize() + "/" + alsDTO.getPage());
		//카테고리(게임 개발, 교양 등등) 출력
		List<String> category=als.searchAllCategory();
		//카테고리 적용 리스트
		List<AdminLectureDomain> lectureByCategory=als.searchLectureByCategory(alsDTO);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("size", alsDTO.getSize());
		model.addAttribute("page", alsDTO.getPage());
		model.addAttribute("category", alsDTO.getCategoryName());
		model.addAttribute("alsDTO", alsDTO);
		model.addAttribute("categoryList", category);
		model.addAttribute("lectByCategory", lectureByCategory);
		model.addAttribute("profileUploadPath", profileUploadPath);

		//헤더에 사용할 페이지명
		model.addAttribute("pageTitle", "교육 과목 관리");
		model.addAttribute("currentUri", req.getRequestURI());
		return "admin/lecture/searchAllLect";
	}
	
	/**
	 * 교육 과목 관리-카테고리 변경 ajax
	 * @param model
	 * @param alsDTO
	 * @return
	 */
	@GetMapping("/searchCategoryLect")
	@ResponseBody 
	public Map<String, Object> searchCategoryLect(Model model, AdminLectureSearchDTO alsDTO) {
		int totalCount=als.countLectureByCategory(alsDTO); //강의 개수
		int totalPage=(int) Math.ceil((double)totalCount/alsDTO.getSize()); //페이지 수
		int size=alsDTO.getSize();
		int page=alsDTO.getPage();
		
	    List<AdminLectureDomain> list = als.searchLectureByCategory(alsDTO);

	    Map<String, Object> result=new HashMap<>();
	    result.put("size", size);
	    result.put("page", page);
	    result.put("totalCount", totalCount);
	    result.put("totalPage", totalPage);
	    result.put("list", list);
	    
	    return result;
	}
	 
	/**
	 * 강의 상태 변경 method
	 * @param lectureId
	 */
	@PostMapping("/updateLectState")
	@ResponseBody
	public Map<String, String> updateLectStop(@RequestParam String lectureId, @RequestParam String action) {
		Map<String, String> status=new HashMap<String, String>();
		
		//중지 버튼을 눌렀을 때
		if("stop".equals(action)) {
			System.out.println(als.disableLecture(lectureId));
			als.disableLecture(lectureId);
			status.put("result", "비공개");
		} else if("open".equals(action)) {
			System.out.println(als.ableLecture(lectureId));
			als.ableLecture(lectureId);
			status.put("result", "공개");
		}
	
		return status;
	}
	
	/**
	 * 강의 관리 화면 처리 method
	 * @param model
	 * @return
	 */
	@GetMapping("/searchNotApprLect")
	public String searchNotApprLect(Model model, AdminLectureSearchDTO alsDTO, HttpServletRequest req) {
		int totalCount=als.countNotApprLect(alsDTO); //강의 개수
		int totalPage=(int) Math.ceil((double)totalCount/alsDTO.getSize()); //페이지 수
		
		List<AdminNotApprLectureDomain> notApprLectureList=als.searchNotApprLectList(alsDTO);
		List<String> category=als.searchAllCategory();
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("size", alsDTO.getSize());
		model.addAttribute("page", alsDTO.getPage());
		model.addAttribute("alsDTO", alsDTO);
		model.addAttribute("notApprLectList", notApprLectureList);
		model.addAttribute("categoryList", category);
		//헤더에 사용할 페이지명
		model.addAttribute("pageTitle", "강의 관리");
		model.addAttribute("currentUri", req.getRequestURI());
		model.addAttribute("profileUploadPath", profileUploadPath);
		
		return "admin/lecture/searchNotApprLect";
	}
	
	/**
	 * 강의 관리-카테고리 변경 ajax
	 * @param model
	 * @param alsDTO
	 * @return
	 */
	@GetMapping("/searchNotApprCategoryLect")
	@ResponseBody 
	public Map<String, Object> searchNotApprCategoryLect(Model model, AdminLectureSearchDTO alsDTO) {
		int totalCount=als.countNotApprLect(alsDTO); //강의 개수
		int totalPage=(int) Math.ceil(totalCount/alsDTO.getSize()); //페이지 수
		int size=alsDTO.getSize();
		int page=alsDTO.getPage();
		
	    List<AdminNotApprLectureDomain> list = als.searchNotApprLectList(alsDTO);
	    
	    Map<String, Object> result=new HashMap<>();
	    result.put("size", size);
	    result.put("page", page);
	    result.put("totalCount", totalCount);
	    result.put("totalPage", totalPage);
	    result.put("list", list);
	    
	    return result;
	}
	
	/**
	 * 강의관리 상세 페이지
	 * @param model
	 * @param lectureId
	 * @return
	 */
	@GetMapping("/searchDetailNotApprLect")
	public String searchDetailNotApprLect(Model model, @RequestParam String lectureId) {
		List<AdminLectureDetailDomain> lectureDetail=als.searchLectureDetail(lectureId);
		List<AdminLectureChapterDomain> lectureChapter=als.searchLectureChapter(lectureId);
		
		model.addAttribute("lecture", lectureDetail);
		model.addAttribute("chapter", lectureChapter);
		model.addAttribute("uploadDocDir", uploadDocDir);
		
		return "admin/lecture/searchDetailNotApprLect";
	}
	
	//파일 다운로드
	@GetMapping("/file")
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
	
	/**
	 * 강의 상태 승인
	 * @param lectureId
	 */
	@PostMapping("/approve")
	@ResponseBody
	public void approve(String lectureId) {
		als.approvalLecture(lectureId);
	}
	
	/**
	 * 강의 상태 거절
	 * @param lectureId
	 * @param reason
	 */
	@PostMapping("/reject")
	@ResponseBody
	public void reject(@RequestParam String lectureId, @RequestParam String reason) {
		als.rejectReason(lectureId, reason);
	}
}