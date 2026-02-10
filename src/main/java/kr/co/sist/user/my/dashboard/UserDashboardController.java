package kr.co.sist.user.my.dashboard;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.user.my.lecture.UserMyLectureDomain;

@RequestMapping("/user/my/dashboard")
@Controller
public class UserDashboardController {

    @Autowired
    private UserDashboardService uds;

    // 대시보드 메인
    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            userId = "user4"; // 테스트용
            session.setAttribute("userId", userId);
        }
        return "user/my/dashboard/index";
    }

    // [1] 최근 학습 강의 (카드 리스트)
    @GetMapping("/searchRecentLectures")
    public String searchRecentLectures(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null) userId = "user4";
        
        List<MyChapterDomain> list = uds.searchRecentLectures(userId);
        model.addAttribute("recentList", list);
        return "user/my/dashboard/recent_list :: recentFragment";
    }

    // [2] 주간 출석 (막대 그래프)
    @GetMapping("/searchMyWeekAttendance")
    public String searchMyWeeklyAttendance(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null) userId = "user4";

        LocalDate today = LocalDate.now();
        // 이번주 일요일~토요일 계산
        LocalDate start = today.minusDays(today.getDayOfWeek().getValue() % 7); 
        LocalDate end = start.plusDays(6); 
        
        AttendRangeDTO range = new AttendRangeDTO(Date.valueOf(start), Date.valueOf(end));
        List<AttendanceDTO> list = uds.searchMyWeekAttendance(range, userId);
        
        model.addAttribute("attendList", list);
        model.addAttribute("rangeType", "주간");
        // 전체 주간 일수(7일) 대비 출석일수 계산용 사이즈
        model.addAttribute("totalDays", 7); 
        
        return "user/my/dashboard/attendance_list :: attendFragment";
    }

    // [3] 월간 출석 (진행률 바)
    @GetMapping("/searchMyMonthlyAttendance")
    public String searchMyMonthlyAttendance(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null) userId = "user4";
        
        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfMonth(1);
        LocalDate end = today.withDayOfMonth(today.lengthOfMonth());
        
        AttendRangeDTO range = new AttendRangeDTO(Date.valueOf(start), Date.valueOf(end));
        List<AttendanceDTO> list = uds.searchMyMonthAttendance(range, userId);
        
        model.addAttribute("attendList", list);
        model.addAttribute("rangeType", "월간");
        model.addAttribute("totalDays", today.lengthOfMonth()); // 이번달 총 일수
        
        return "user/my/dashboard/attendance_list :: attendFragment";
    }

    // [4] 강의 상세보기 (요청하신 단순 텍스트 페이지)
    @GetMapping("/detail")
    public String detail(@RequestParam("myLectId") String myLectId, Model model) {
        // 강의 정보를 가져와서 이름만 추출
        UserMyLectureDomain lect = uds.getLectureDetail(myLectId);
        
        // 화면에 뿌릴 문구 설정
        String msg = "{" + lect.getLectName() + "} 에 대한 상세보기입니다.";
        model.addAttribute("msg", msg);
        
        return "user/my/dashboard/simple_lecture_detail";
    }
}