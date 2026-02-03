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
    
 // UserDashboardController.java

    // [수정] required = false로 변경하여 파라미터가 없어도 에러 안 나게 함
    @GetMapping("/index")
    public String index(@RequestParam(name = "myLectId", required = false) String myLectId, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null) return "redirect:/";
        
        // 1. myLectId가 없으면? -> 가장 최근에 학습한 강의 ID를 찾아서 자동으로 설정
        if (myLectId == null) {
            // 최근 학습 강의 목록 가져오기
            List<MyChapterDomain> recentList = uds.searchRecentLectures(userId);
            
            if (!recentList.isEmpty()) {
                // 최근 학습한 강의가 있으면 그 강의의 ID를 사용 (여기서는 챕터ID나 강의ID를 잘 매핑해야 함)
                // *주의: 현재 MyChapterDomain에는 myLectId가 없을 수 있음. 
                // 임시 방편: 최근 기록이 있으면 그냥 첫 번째 기록 보여주기 (없으면 빈 상태로)
                
                // (만약 최근 학습 강의 ID를 알 수 없다면, 그냥 빈 껍데기만 보여주도록 처리)
                // 여기서는 "전체 요약 모드"라고 가정하고 lect 객체를 null로 두지 않기 위해 빈 객체 생성
                UserMyLectureDomain dummy = new UserMyLectureDomain();
                dummy.setLectName("최근 학습 강의를 선택해주세요");
                dummy.setInstName("-");
                dummy.setProgRate(0);
                model.addAttribute("lect", dummy);
            } else {
                UserMyLectureDomain dummy = new UserMyLectureDomain();
                dummy.setLectName("수강 중인 강의가 없습니다");
                dummy.setInstName("-");
                dummy.setProgRate(0);
                model.addAttribute("lect", dummy);
            }
        } else {
            // 파라미터가 있으면 해당 강의 상세 정보 조회
            UserMyLectureDomain lectInfo = uds.getLectureDetail(myLectId);
            model.addAttribute("lect", lectInfo);
        }
        
        // 2. 최근 학습 강의 조회
        searchRecentLectures(session, model);
        
        // 3. 주간 출석 조회
        searchMyWeeklyAttendance(session, model);
        
        return "user/my/dashboard/index";
    }

    // 최근 학습 강의 2개
    @GetMapping("/searchRecentLectures")
    public String searchRecentLectures(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        List<MyChapterDomain> list = uds.searchRecentLectures(userId);
        model.addAttribute("recentList", list);
        return "user/my/dashboard/recent_list :: recentFragment"; // (선택) AJAX용 조각
    }

    // 주간 출석 기록
    @GetMapping("/searchMyWeekAttendance")
    public String searchMyWeeklyAttendance(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        
        // 현재 날짜 기준 주간 범위 계산 (일~토)
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(today.getDayOfWeek().getValue() % 7); // 일요일
        LocalDate end = start.plusDays(6); // 토요일
        
        AttendRangeDTO range = new AttendRangeDTO(Date.valueOf(start), Date.valueOf(end));
        List<AttendanceDTO> list = uds.searchMyWeekAttendance(range, userId);
        
        model.addAttribute("attendList", list);
        model.addAttribute("rangeType", "주간");
        return "user/my/dashboard/attendance_list :: attendFragment";
    }

    // 월간 출석 기록
    @GetMapping("/searchMyMonthlyAttendance")
    public String searchMyMonthlyAttendance(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        
        // 현재 날짜 기준 월간 범위 계산 (1일~말일)
        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfMonth(1);
        LocalDate end = today.withDayOfMonth(today.lengthOfMonth());
        
        AttendRangeDTO range = new AttendRangeDTO(Date.valueOf(start), Date.valueOf(end));
        List<AttendanceDTO> list = uds.searchMyMonthAttendance(range, userId);
        
        model.addAttribute("attendList", list);
        model.addAttribute("rangeType", "월간");
        return "user/my/dashboard/attendance_list :: attendFragment";
    }
    
 // UserDashboardController.java에 추가

 // 1. 단순 학습하기 페이지 (요청하신 대로 텍스트 + 홈 링크)
 @GetMapping("/learning")
 public String learning() {
     return "user/my/dashboard/simple_learning";
 }

 // 2. 강의 상세 정보 조회 페이지 (기존 index 메소드 활용 또는 새로 생성)
 @GetMapping("/detail")
 public String detail(@RequestParam("myLectId") String myLectId, HttpSession session, Model model) {
     String userId = (String) session.getAttribute("userId");
     if(userId == null) return "redirect:/";
     
     // 강의 상세 정보 가져오기
     UserMyLectureDomain lectInfo = uds.getLectureDetail(myLectId);
     model.addAttribute("lect", lectInfo);
     
     // (여기서 최근 학습, 출석 기록 등도 불러오면 됨 - 기존 로직 활용)
     // searchRecentLectures(session, model);
     // searchMyWeeklyAttendance(session, model);
     
     return "user/my/dashboard/index"; // 상세 정보가 나오는 대시보드 화면
 }
    
    
}