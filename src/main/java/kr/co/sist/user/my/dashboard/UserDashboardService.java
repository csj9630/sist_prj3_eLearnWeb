package kr.co.sist.user.my.dashboard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.sist.user.my.lecture.UserMyLectureDomain;

@Service
public class UserDashboardService {

    @Autowired
    private UserDashboardMapper mapper;

    // 최근 학습 강의
    public List<MyChapterDomain> searchRecentLectures(String userId) {
        return mapper.selectRecentLectures(userId);
    }

    /**
     * 주간 학습 현황
     * 이번 주 월(0)~일(6) 까지 각 요일별 출석 여부를 Boolean의 리스트로 반환
     */
    public List<Boolean> getWeeklyStatus(String userId) {
        List<String> attendDates = mapper.selectWeeklyAttendance(userId);
        List<Boolean> weeklyStatus = new ArrayList<>();

        // 오늘 날짜 기준 이번 주 월요일 구하기
        LocalDate today = LocalDate.now();
        // DayOfWeek: 월(1) ~ 일(7). Monday를 기준으로 차이를 빼서 월요일 날짜 계산
        LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1);

        // 월~일 (7일간) 반복하며 출석했는지 체크
        for (int i = 0; i < 7; i++) {
            LocalDate targetDay = monday.plusDays(i);
            boolean isAttended = attendDates.contains(targetDay.toString()); // YYYY-MM-DD 비교
            weeklyStatus.add(isAttended);
        }
        return weeklyStatus;
    }

    /**
     * 월간 학습 그래프
     * 이번 달 1일 ~ 말일까지의 일별 수강 횟수를 Integer의 리스트로 반환.
     * DB에 없는 날짜는 0으로 채움.
     */
    public List<Integer> getMonthlyData(String userId) {
        List<Map<String, Object>> dbData = mapper.selectMonthlyLearningCount(userId);
        List<Integer> monthlyCounts = new ArrayList<>();

        // 이번 달의 마지막 날짜 (28, 30, 31일)
        int lastDay = LocalDate.now().lengthOfMonth();

        // 1일부터 말일까지 배열 초기화(0으로 채움). 1-based index 편의상
        int[] counts = new int[lastDay + 1];

        // DB 데이터를 배열에 매핑
        for (Map<String, Object> map : dbData) {
            int day = Integer.parseInt(String.valueOf(map.get("day"))); 
            int cnt = Integer.parseInt(String.valueOf(map.get("cnt")));
            if(day <= lastDay) {
                counts[day] = cnt;
            }//end if
        }//end for

        // List로 변환(index 1부터 추가)
        for (int i = 1; i <= lastDay; i++) {
            monthlyCounts.add(counts[i]);
        }//end for

        return monthlyCounts;
    }//getMonthlyData
    
    
    
    //강의 상세 조회
    public UserMyLectureDomain getLectureDetail(String myLectId) {
        return mapper.selectLectureDetail(myLectId);
    }//UserMyLectureDomain
    
}//class