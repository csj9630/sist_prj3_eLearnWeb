package kr.co.sist.user.my.dashboard;

import java.util.List;
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

    // 주간 출석
    public List<AttendanceDTO> searchMyWeekAttendance(AttendRangeDTO arDTO, String userId) {
        return mapper.selectMyAttendance(arDTO, userId);
    }

    // 월간 출석
    public List<AttendanceDTO> searchMyMonthAttendance(AttendRangeDTO arDTO, String userId) {
        return mapper.selectMyAttendance(arDTO, userId);
    }
    
    // [추가] 강의 상세 조회
    public UserMyLectureDomain getLectureDetail(String myLectId) {
        return mapper.selectLectureDetail(myLectId);
    }
}