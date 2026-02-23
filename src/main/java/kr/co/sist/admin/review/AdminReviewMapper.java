package kr.co.sist.admin.review;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminReviewMapper {
	
	public int selectReviewTotalCnt(AdminReviewRangeDTO rangeDTO) throws SQLException;
	
	public List<AdminReviewDomain> selectRangeReview(AdminReviewRangeDTO rangeDTO) throws SQLException;
	
	public AdminReviewDomain selectReviewDetailAdmin(String reviewId) throws SQLException;
	
	public int deleteReview(AdminReviewDTO reviewDTO) throws SQLException;
	
} // interface
