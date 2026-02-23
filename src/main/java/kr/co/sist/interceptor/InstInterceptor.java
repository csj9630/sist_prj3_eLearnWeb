package kr.co.sist.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class InstInterceptor implements HandlerInterceptor{

	@Value("${inst.loginFrm}")
	private String loginFrmURL;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//로그인 화면일 때는 인터셉터 스킵. 무한 루프에 빠질 가능성 배제하기 위해 사용
		if (request.getRequestURI().contains("loginFrm")) {
	        return true;
	    }
		boolean flag = false;
		
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("instId");
		
		//로그인 상태 확인
		flag = obj != null;
		
		if( !flag ) { //로그인이 되어있지 않은 상황.
			response.sendRedirect(loginFrmURL);
		}//end if
		return flag;
	}

	
}
