package kr.co.sist.user.lecture.chapter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;
@Mapper
public interface ChapterMapper {
	public List<ChapterDomain> selectAllChapter() throws PersistenceException;
	public int selectTest();
}
