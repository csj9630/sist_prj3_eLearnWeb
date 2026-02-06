package kr.co.sist.user.my.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    // 1. 메인 페이지 연결 (http://localhost:8080/ 접속 시)
    @GetMapping("/")
    public String main() {
        // templates/user/index.html 파일을 찾아감
        return "user/index"; 
    }

    // 2. 테스트용 로그인 기능 (버튼 누르면 세션에 user1 저장)
    @GetMapping("/user/loginTest")
    public String loginTest(HttpSession session) {
        // DB에 존재하는 테스트 계정 정보 저장
        session.setAttribute("userId", "user4");
        session.setAttribute("userName", "남지우");
        
        // 로그인 후 메인 페이지로 다시 돌아감
        return "redirect:/"; 
    }
}