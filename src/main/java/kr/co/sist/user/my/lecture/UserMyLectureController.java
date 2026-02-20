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

<<<<<<< HEAD
    @GetMapping("/searchMyLecture")
    public String searchMyLecture(HttpSession session, Model model) {
        // 1. 세션에서 아이디 가져오기
=======
    @GetMapping("")
    public String myLectureList(
            @RequestParam(value = "title", required = false) String title, // 검색어 (없을 수도 있음)
            HttpSession session, Model model) {

>>>>>>> refs/heads/dev0220-2
        String userId = (String) session.getAttribute("userId");
        
<<<<<<< HEAD
        // 2. 없으면 테스트 계정(user4) 자동 설정
=======
        // (테스트용) 로그인 안 되어 있으면 강제 설정
>>>>>>> refs/heads/dev0220-2
        if(userId == null) {
<<<<<<< HEAD
            userId = "user4";
=======
            userId = "user1"; 
>>>>>>> refs/heads/dev0220-2
            session.setAttribute("userId", userId);
        }

<<<<<<< HEAD
        // 3. 서비스 호출
        List<UserMyLectureDomain> list = umls.searchMyLectureList(userId);
=======
        // title이 null이면 전체 조회, 있으면 검색 조회
        List<UserMyLectureDomain> list = umls.searchMyLectureList(userId, title);
        
>>>>>>> refs/heads/dev0220-2
        model.addAttribute("myLectureList", list);
<<<<<<< HEAD

=======
        
>>>>>>> refs/heads/dev0220-2
        return "user/my/lecture/my_lecture_list";
    }

}//class