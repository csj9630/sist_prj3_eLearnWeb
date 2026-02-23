package kr.co.sist.instructor.review;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InstructorReviewMapper {
	
	public int selectReviewTotalCnt(InstructorReviewRangeDTO rangeDTO) throws SQLException;
	
	public List<InstructorReviewDomain> selectRangeReview(InstructorReviewRangeDTO rangeDTO) throws SQLException;
	
	public InstructorReviewDomain selectReviewDetailInstructor(String reviewId) throws SQLException;
	
} // interface
