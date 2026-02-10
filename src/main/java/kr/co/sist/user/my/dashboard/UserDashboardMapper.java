package kr.co.sist.user.my.dashboard;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.user.my.lecture.UserMyLectureDomain;

@Mapper
public interface UserDashboardMapper {
    //최근 학습 강의 2개 조회
    List<MyChapterDomain> selectRecentLectures(String userId);
    
    //주간 출석 조회
    List<String> selectWeeklyAttendance(String userId);
    
    //월간 학습 건수 조회
    List<Map<String, Object>> selectMonthlyLearningCount(String userId);
    
    //강의 세부 정보 조회 .대시보드용
    UserMyLectureDomain selectLectureDetail(String myLectId);

}//interface