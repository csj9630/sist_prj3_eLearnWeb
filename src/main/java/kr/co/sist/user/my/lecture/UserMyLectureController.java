package kr.co.sist.user.my.lecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user/my/lecture")
@Controller
public class UserMyLectureController {

    @Autowired
    private UserMyLectureService umls;

    @GetMapping("/searchMyLecture")
    public String searchMyLecture(HttpSession session, Model model) {
        // 세션에서 아이디 가져오기
        String userId = (String) session.getAttribute("userId");
        
        // 비로그인 시 메인으로 리다이렉트
        if(userId == null) {
            return "redirect:/";
        }

        // 서비스 호출
        List<UserMyLectureDomain> list = umls.searchMyLectureList(userId);
        model.addAttribute("myLectureList", list);

        // HTML 파일 위치
        return "user/my/lecture/my_lecture_list";
    }
}