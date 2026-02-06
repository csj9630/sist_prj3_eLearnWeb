package kr.co.sist.user.lecture.review;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

@Mapper
public interface UserReviewMapper {
	
	// 신규 공지사항을 등록
	// <insert id = "insertReview" parameterType = "userReviewDTO">
	public void insertReview(UserReviewDTO reviewDTO) throws PersistenceException;
	
	// (만일 키워드가 있다면 키워드를 포함하는 내용의)공지사항 개수를 조회
	// <select id = "selectReviewTotalCnt" parameterType = "userReviewRangeDTO" resultType = "int">
	public int selectReviewTotalCnt(UserReviewRangeDTO rangeDTO) throws SQLException;
	
	// 한 페이지에 출력할(만일 키워드가 있다면 키워드를 포함하는 내용의) 공지사항을 조회
	// <select id = "selectRangeReview" parameterType = "userReviewRangeDTO" resultType = "userReviewDomain">
	public List<UserReviewDomain> selectRangeReview(UserReviewRangeDTO rangeDTO) throws SQLException;
	
	// 공지사항 아이디를 받아서 해당하는 공지사항 하나를 자세히 조회
	// <select id = "selectReviewDetail" parameterType = "Integer" resultType = "userReviewDomain">
	public UserReviewDomain selectReviewDetailUser(String reviewId) throws SQLException;
	
	// 공지사항 아이디를 받아서 해당하는 공지사항 하나를 자세히 조회
	// <select id = "selectReviewDetail" parameterType = "Integer" resultType = "userReviewDomain">
	public UserReviewDomain selectReviewDetailInstructor(String reviewId) throws SQLException;
	
	// 공지사항 아이디를 받아서 해당하는 공지사항 하나를 자세히 조회
	// <select id = "selectReviewDetail" parameterType = "Integer" resultType = "userReviewDomain">
	public UserReviewDomain selectReviewDetailAdmin(String reviewId) throws SQLException;
	
	// 공지사항 아이디와 관리자 아이디를 입력 받아 관리자임을 확인 후 해당 공지사항의 내용과 아이피를 변경
	// <update id = "updateReview" parameterType = "userReviewDTO">
	public int updateReview(UserReviewDTO reviewDTO) throws SQLException;
	
	// 공지사항 아이디와 관리자 아이디를 입력 받아 관리자임을 확인 후 해당 공지사항을 삭제
	// <delete id = "deleteReview" parameterType = "userReviewDTO">
	public int deleteReview(UserReviewDTO reviewDTO) throws SQLException;
	
} // interface
