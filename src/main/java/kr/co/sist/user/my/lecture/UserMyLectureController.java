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
        // 1. 세션에서 아이디 가져오기
        String userId = (String) session.getAttribute("userId");
        
        // 2. 없으면 테스트 계정(user4) 자동 설정
        if(userId == null) {
            userId = "user4";
            session.setAttribute("userId", userId);
        }

        // 3. 서비스 호출
        List<UserMyLectureDomain> list = umls.searchMyLectureList(userId);
        model.addAttribute("myLectureList", list);

        return "user/my/lecture/my_lecture_list";
    }
}