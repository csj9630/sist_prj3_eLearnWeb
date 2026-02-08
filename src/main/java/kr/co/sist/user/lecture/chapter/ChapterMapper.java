package kr.co.sist.user.lecture.chapter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;
@Mapper
public interface ChapterMapper {
	public int selectTest();
	public List<ChapterDomain> selectChapterProgress(ChapterDTO cdto) throws PersistenceException;
}
