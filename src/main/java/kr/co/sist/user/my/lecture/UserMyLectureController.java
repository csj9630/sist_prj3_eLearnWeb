package kr.co.sist.user.my.lecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/my/lecture")
public class UserMyLectureController {

    @Autowired
    private UserMyLectureService umls;

    //http://localhost:8080/user/my/lecture
    @GetMapping("")
    public String index(HttpSession session, Model model) {
        // user1 강제 로그인 (테스트용. 세션에 user1 데이터 저장. 후에 복호화데이터 받아올 예정)
        String userId = (String) session.getAttribute("userId");
        if(userId == null) {
            userId = "user1"; 
            session.setAttribute("userId", userId);
        }//end if
        
        //검색 로직 재사용
        return searchMyLecture(session, model);
    }//index

    // 검색/리스트 조회
    @GetMapping("/searchMyLecture")
    public String searchMyLecture(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        
        // 혹시 아이디를 안받아온다면 userId를 user1으로 설정. 세션에 넘김
        if(userId == null) {
            userId = "user1";
            session.setAttribute("userId", userId);
        }//end if

        // 서비스 호출 -> DB 조회 -> 리스트 반환
        List<UserMyLectureDomain> list = umls.searchMyLectureList(userId);
        
        // 화면으로 데이터 전송
        model.addAttribute("myLectureList", list);

        return "user/my/lecture/my_lecture_list";
    }//searchMyLecture

}//class