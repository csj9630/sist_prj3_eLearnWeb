package kr.co.sist.user.my.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class TestLoginController {

    // 사용자 로그인 전환 -> [수정] 메인 페이지(/)로 이동하여 메뉴 버튼을 보여줌
    @GetMapping("/dev/login/user")
    public String loginUser(@RequestParam(name="id") String userId, HttpSession session) {
        
        // 기존 세션 값 제거 (충돌 방지)
        session.removeAttribute("userId");
        session.removeAttribute("instId");
        
        // 새로운 사용자 ID 설정
        session.setAttribute("userId", userId);
        
        System.out.println("✅ [테스트 모드] 사용자 전환 완료: " + userId);
        
        // [핵심 수정] 로그인 후 '버튼들이 있는 메인 페이지'로 돌아갑니다.
        return "redirect:/"; 
    }

    // 강사 로그인 전환 -> 강사 수익 페이지로 이동 (기존 유지)
    @GetMapping("/dev/login/inst")
    public String loginInst(@RequestParam(name="id") String instId, HttpSession session) {
        
        session.removeAttribute("userId");
        session.removeAttribute("instId");
        
        session.setAttribute("instId", instId);
        
        System.out.println("✅ [테스트 모드] 강사 전환 완료: " + instId);
        
        // 강사 전용 페이지로 이동
        return "redirect:/instructor/payment/inst_payment"; 
    }
}