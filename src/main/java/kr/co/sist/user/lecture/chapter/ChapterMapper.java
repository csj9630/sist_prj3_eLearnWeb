package kr.co.sist.user.lecture.chapter;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;
@Mapper
public interface ChapterMapper {
	public int selectTest();
	public List<ChapterDomain> selectChapterList(String lectId) throws PersistenceException;
	public List<StuChapterDomain> selectChapterProgress(ChapterDTO cdto) throws PersistenceException;
	public List<VideoDomain> selectVideoData(ChapterDTO cdto) throws PersistenceException;
	public int insertMyChapter(VideoDTO vdto) throws PersistenceException, SQLException;
	public int mergeRecordtoMyChapter(VideoDTO vdto) throws PersistenceException, SQLException;
	public int insertStuAttendence(String userId) throws PersistenceException, SQLException;
}
