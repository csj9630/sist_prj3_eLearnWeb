package kr.co.sist.user.lecture.test;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import tools.jackson.databind.ObjectMapper;

@RequestMapping("/user/lecture/test")
@Controller
public class StuTestController {



	@Autowired
	private StuTestService ss;
	@Autowired
	private StuTestRecordService srs;
	
	
	// 시험 응시 이력 조회 StuTestService
	@GetMapping("/checkTest")
	@ResponseBody
	public JSONObject checkTest(Model model,StuCheckTestDTO sctDTO) {
		JSONObject jsonObject = new JSONObject();
		boolean result = ss.searchCheckTest(sctDTO);
		jsonObject.put("result", result);
		return jsonObject;
	}// checkTest

	// 시험 이력 삭제
	@GetMapping("/stuRemoveTestRecord")
	public String stuRemoveTestRecord(Model model, StuCheckTestDTO sctDTO, String lectId, HttpSession session) {
		System.out.println("삭제 시작");
		System.out.println("sctDTO : " + sctDTO);
		ss.resetTestRecord(sctDTO);
		System.out.println("삭제 완료");
		return "redirect:/user/lecture/test/stuTestFrm?lectId=" + lectId + "&userId=" + sctDTO.getUserId();
	}// stuRemoveTestRecord
	
	// 시험 페이지 Frm
	@GetMapping("/stuTestFrm")
	public String instTestFrm(Model model,StuCheckTestDTO sctDTO, String lectId, String userId,HttpSession session, @RequestParam(defaultValue = "false") boolean isReview
			) {
		List<StuTestViewDomain> stdList = ss.searchTest(lectId);
		
		model.addAttribute("stdList", stdList);
		//향후 session으로 변경 예정
	    model.addAttribute("userId", userId);
	    model.addAttribute("isReview", isReview);
		
	 // 해설 모드일 경우: 사용자의 과거 답안 및 정답/해설 데이터 추가 조회
	    if(isReview) {
	        List<StuTestExplanationDomain> reviewList = ss.searchExplanation(sctDTO);
	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            String reviewDataJson = mapper.writeValueAsString(reviewList);
	            model.addAttribute("reviewDataJson", reviewDataJson);
	            System.out.println("JSON 데이터 확인: " + reviewDataJson); // 서버 콘솔에서 데이터 유무 확인
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
		return "/user/lecture/test/userTestFrm";
	}// instTestFrm
	

	// 시험문제 제출 + 채점 후 시험 이력 및 응시 내역 테이블에 값 저장.
	@PostMapping("/submitTestProcess")
	@ResponseBody
	public List<StuTestAnswerDomain> submitTestProcess(Model model, HttpSession session, StuTestRecordDTO strDTO, StuAnswerDTO saDTO,  String lectId, HttpServletRequest request) {
		//StuTestRecordDTO =내 시험 이력 테이블, StuAnswerDTO=시험 응시 테이블
		//세션에서 유저아이디 가져올 때 사용하자
		//String userId = (String) session.getAttribute("userId");
		String ip = request.getRemoteAddr();
		strDTO.setIp(ip);
		//채점 진행 및 내시험이력에 값 저장.
		List<StuTestAnswerDomain> staDomainList = srs.searchQuestionAnswer(lectId, saDTO, strDTO);
		srs.insertMyTest(strDTO);
		//Mapper에서 my_test_id 시퀀스 값을 받아왔던걸, 진짜 써야하는 StuAnswerDTO에 넣어줌.(시험 응시 테이블)
		saDTO.setMy_test_id(strDTO.getMy_test_id());
		//시험 응시 테이블에 값 삽입.
		srs.insertTestExamination(saDTO);
		
		return staDomainList;
	}// instTestFrm

	// 시험 이력, 점수 조회
	@GetMapping("/stuScore")
	public String stuScore(Model model, int lectId, HttpSession session) {

		return "/user/lecture/test/stuTestFrm";
	}// stuScore

	// 시험 답안 조회
	@GetMapping("/stuAnswer")
	public String stuAnswer(Model model, int test_id, String stu_id, HttpSession session) {

		return "/user/lecture/test/stuTestFrm";
	}// stuAnswer

}
