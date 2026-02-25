package kr.co.sist.user.my.dashboard;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.user.my.lecture.UserMyLectureDomain;
import kr.co.sist.user.my.lecture.UserMyLectureService;

@Controller
@RequestMapping("/user/my/dashboard")
public class UserDashboardController {

    @Autowired
    private UserDashboardService userDashboardService;
    
    @Autowired
    private UserMyLectureService userMyLectureService;
    
  //강의 썸네일 이미지 저장 경로
  	@Value("${file.lecture.img-path}")
  	private String imgPath;

    @GetMapping({"","/user_dashboard"}) 
    public String dashboard(HttpSession session, Model model) {
        // 1. 세션 체크 (테스트용. user1)
        String userId = (String) session.getAttribute("userId");
//        if (userId == null) {
//            userId = "user1";
//            session.setAttribute("userId", userId);
//        }//end if

        //최근 학습 강의(2개)
        List<UserMyLectureDomain> list = userMyLectureService.searchMyLectureList(userId,null);
        //상위 2개만 자르기
        List<UserMyLectureDomain> recentList = list.stream().limit(2).collect(Collectors.toList());
        model.addAttribute("recentList", recentList);
        model.addAttribute("imgPath", imgPath);
        
        //주간 학습 현황 : T,F형태의 리스트
        List<Boolean> weeklyStatus = userDashboardService.getWeeklyStatus(userId);
        model.addAttribute("weeklyStatus", weeklyStatus);

        //월간 학습 현황 그래프
        //완료 수업수에 의한 선형 그래프
        List<Integer> monthlyData = userDashboardService.getMonthlyData(userId);
        model.addAttribute("monthlyData", monthlyData);

        return "user/my/dashboard/user_dashboard";
    }//dashboard
}//class