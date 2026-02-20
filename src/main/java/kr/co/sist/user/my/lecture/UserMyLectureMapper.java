package kr.co.sist.user.my.lecture;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMyLectureMapper {
    // 내 수강 목록 조회
    List<UserMyLectureDomain> selectMyLectureList(
    		@Param("userId") String userId,
    		@Param("title") String title);
}//interface