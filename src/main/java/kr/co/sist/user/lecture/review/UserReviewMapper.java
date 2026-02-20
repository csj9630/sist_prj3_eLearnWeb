package kr.co.sist.user.lecture.review;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

@Mapper
public interface UserReviewMapper {
	
	// <insert id = "insertReview" parameterType = "userReviewDTO">
	public void insertReview(UserReviewDTO reviewDTO) throws PersistenceException;
	
	// <select id = "selectReviewTotalCnt" parameterType = "userReviewRangeDTO" resultType = "int">
	public int selectReviewTotalCnt(UserReviewRangeDTO rangeDTO) throws SQLException;
	
	// <select id = "selectReviewTotalCnt" parameterType = "userReviewRangeDTO" resultType = "int">
	public double selectReviewAvgScore(UserReviewRangeDTO rangeDTO) throws SQLException;
	
	// <select id = "selectRangeReview" parameterType = "userReviewRangeDTO" resultType = "userReviewDomain">
	public List<UserReviewDomain> selectRangeReview(UserReviewRangeDTO rangeDTO) throws SQLException;
	
	// <select id = "selectReviewDetail" parameterType = "Integer" resultType = "userReviewDomain">
	public UserReviewDomain selectReviewDetailUser(String reviewId) throws SQLException;
	
	// <select id = "selectReviewDetail" parameterType = "Integer" resultType = "userReviewDomain">
	public UserReviewDomain selectReviewDetailInstructor(String reviewId) throws SQLException;
	
	// <select id = "selectReviewDetail" parameterType = "Integer" resultType = "userReviewDomain">
	public UserReviewDomain selectReviewDetailAdmin(String reviewId) throws SQLException;
	
	// <update id = "updateReview" parameterType = "userReviewDTO">
	public int updateReview(UserReviewDTO reviewDTO) throws SQLException;
	
	// <delete id = "deleteReview" parameterType = "userReviewDTO">
	public int deleteReview(UserReviewDTO reviewDTO) throws SQLException;
	
	// <select id="selectMyReview" parameterType="userReviewDTO" resultType="userReviewDomain">
	public UserReviewDomain selectMyReview(UserReviewDTO reviewDTO) throws SQLException;
	
} // interface
