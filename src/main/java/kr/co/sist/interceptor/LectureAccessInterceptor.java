package kr.co.sist.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.sist.common.lecture.CommonLectureService;

@Component
public class LectureAccessInterceptor implements HandlerInterceptor{

	@Autowired
    private CommonLectureService common;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = true;
		
		HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        
        // GET이나 POST로 넘어온 lectId 파라미터 추출
        String lectId = request.getParameter("lectId");

        //lectId가 아예 없거나, 유저 아이디가 없거나, 유저가 수강한 아이디가 아니면 바로 에러페이지로 거부 메시지 리턴.
        if (lectId == null || userId == null || !common.isMyLecture(userId, lectId)) {
            // 컨트롤러의 model.addAttribute("msg", "...") 대신 오랜만에 request에 속성 담아 응답
        	request.setAttribute("msg", "[IC-403] 수강 중인 강의가 아닙니다.");
   
        	//에러페이지로 응답
        	//주의: 뷰 파일 경로가 아니라, 해당 뷰를 보여주는 '컨트롤러의 URL 매핑 주소'를 적기.
        	request.getRequestDispatcher("/common/err").forward(request, response);
            
            result = false; // 컨트롤러로 진입하지 못하게 차단
        }//if

        return result; // 권한이 있으면 정상적으로 컨트롤러 실행
	}//method

	
}//class
