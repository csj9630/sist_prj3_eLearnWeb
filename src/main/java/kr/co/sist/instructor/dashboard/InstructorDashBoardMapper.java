package kr.co.sist.instructor.dashboard;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InstructorDashBoardMapper {
	
	public int selectCountLect(@Param("instId") String instId);
	public int selectScoreReview(@Param("instId") String instId);
	public int selectCountUser(@Param("instId") String instId);
	public int selectInstProfit(@Param("instId") String instId);
	
	public List<InstructorDashBoardDomain> selectInstLecture(@Param("instId") String instId);
}