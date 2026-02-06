package kr.co.sist.user.my.dashboard;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.user.my.lecture.UserMyLectureDomain;

@Mapper
public interface UserDashboardMapper {
    // 1. 최근 학습 강의 2개 조회
    List<MyChapterDomain> selectRecentLectures(String userId);
    
    // 2. 출석 기록 조회 (주간/월간 공용)
    // 파라미터가 2개 이상일 때는 @Param을 써야 XML에서 인식합니다.
    List<AttendanceDTO> selectMyAttendance(@Param("arDTO") AttendRangeDTO arDTO, @Param("userId") String userId);
    
    // 3. 강의 세부 정보 조회 (대시보드 메인용)
    UserMyLectureDomain selectLectureDetail(String myLectId);
}