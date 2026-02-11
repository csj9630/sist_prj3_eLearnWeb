package kr.co.sist.user.lecture.chapter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;
@Mapper
public interface ChapterMapper {
	public int selectTest();
	public List<ChapterDomain> selectChapterList(String lectId) throws PersistenceException;
	public List<StuChapterDomain> selectChapterProgress(ChapterDTO cdto) throws PersistenceException;
	public List<VideoDomain2> selectVideoData(ChapterDTO cdto) throws PersistenceException;
}
