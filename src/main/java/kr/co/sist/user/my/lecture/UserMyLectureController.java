package kr.co.sist.user.my.lecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/my/lecture")
public class UserMyLectureController {

    @Autowired
    private UserMyLectureService umls;

    @GetMapping("")
    public String myLectureList(
            @RequestParam(value = "title", required = false) String title, // 검색어 (없을 수도 있음)
            HttpSession session, Model model) {

        String userId = (String) session.getAttribute("userId");
        
        // (테스트용) 로그인 안 되어 있으면 강제 설정
        if(userId == null) {
            userId = "user1"; 
            session.setAttribute("userId", userId);
        }

        // title이 null이면 전체 조회, 있으면 검색 조회
        List<UserMyLectureDomain> list = umls.searchMyLectureList(userId, title);
        
        model.addAttribute("myLectureList", list);
        
        return "user/my/lecture/my_lecture_list";
    }

}//class