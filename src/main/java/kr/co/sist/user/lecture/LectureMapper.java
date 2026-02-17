package kr.co.sist.user.lecture;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;
@Mapper
public interface LectureMapper {
	public int selectLectureCount(LectureRangeDTO rangeDTO) throws PersistenceException, SQLException;
	public List<LectureDomain> selectLectureList(LectureRangeDTO rangeDTO) throws PersistenceException, SQLException;
	public String selectLectureDetail(LectureRangeDTO rangeDTO) throws PersistenceException, SQLException;
	public String selectLectureReviews(LectureRangeDTO rangeDTO) throws PersistenceException, SQLException;


}
