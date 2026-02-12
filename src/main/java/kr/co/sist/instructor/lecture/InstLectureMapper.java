package kr.co.sist.instructor.lecture;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;
@Mapper
public interface InstLectureMapper {
	public List<InstLectureDomain> selectInstLectureList(String instId) throws PersistenceException;
}
