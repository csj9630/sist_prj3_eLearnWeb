package kr.co.sist.instructor.lecture.chapter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface InstChapterMapper {
	public List<InstChapterDTO> selectChapterList(String lectId);
    public InstChapterDTO selectChapterDetail(String chptrId);
    public int insertChapter(InstChapterDTO dto);
    public int updateChapter(InstChapterDTO dto);
    public int deleteChapter(String chptrId);
	
}
