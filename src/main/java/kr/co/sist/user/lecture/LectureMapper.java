package kr.co.sist.user.lecture;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

import kr.co.sist.user.lecture.board.CategoryDomain;
import kr.co.sist.user.lecture.board.LectureDomain;
import kr.co.sist.user.lecture.board.LectureRangeDTO;
import kr.co.sist.user.lecture.board.SkillDomain;
import kr.co.sist.user.lecture.detail.LectureDetailDomain;
import kr.co.sist.user.lecture.review.UserReviewDomain;
@Mapper
public interface LectureMapper {
	public int selectLectureCount(LectureRangeDTO rangeDTO) throws PersistenceException, SQLException;
	public List<LectureDomain> selectLectureList(LectureRangeDTO rangeDTO) throws PersistenceException, SQLException;
	public LectureDetailDomain selectLectureDetail(String lectId) throws PersistenceException, SQLException;
	public List<UserReviewDomain> selectLectureReviews(String lectId) throws PersistenceException, SQLException;
	public List<SkillDomain> selectAllSkills() throws PersistenceException, SQLException;
	public List<CategoryDomain> selectAllCategories() throws PersistenceException, SQLException;


}
