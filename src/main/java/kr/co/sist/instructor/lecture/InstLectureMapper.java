package kr.co.sist.instructor.lecture;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;
@Mapper
public interface InstLectureMapper {
	public List<InstLectureDomain> selectInstLectureList(InstLectureSearchDTO sdto) throws PersistenceException;
	public int insertLecture(InstLectureDTO ldto) throws PersistenceException;
	public int insertLectureSkills(@Param("lectId") String lectId, @Param("skillIds") List<String> skillIds) throws PersistenceException;
	public int updateAvailability(@Param("lectId")String lectId, @Param("availability")int availability ) throws PersistenceException;
	public InstLectureDTO selectInstLectureDetail(String lectId);
	public List<String> selectLectureSkills(String lectId);
	public int  updateLecture(InstLectureDTO ldto);
	public void  deleteLectureSkills(String lectId) ;
}
