package kr.co.sist.user.lecture;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;
@Mapper
public interface LectureMapper {
	public List<LectureDomain> getLectureList() throws PersistenceException;
}
