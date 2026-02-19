package kr.co.sist.admin.lecture;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminLectureMapper {

	public List<String> selectAllCategory();
	//강의 공개
	public int updateOpen(String lectureId);
	//강의 비공개
	public int updateStop(String lectureId);
	//강의 승인 
	public int updateApproval(String lectureId);
	public int updateAvailability(String lectureId, int availability);
	//교육 과목 관리 강의 개수
	public int selectLectureCount(AdminLectureSearchDTO alsDTO);
	//강의 관리 강의 개수
	public int selectNotApprCount(AdminLectureSearchDTO alsDTO);
	
	public List<AdminLectureDomain> selectLectureByCategory(AdminLectureSearchDTO alsDTO);
	public List<AdminNotApprLectureDomain> selectNotApprLectList(AdminLectureSearchDTO alsDTO);
	public List<AdminLectureDetailDomain> selectLectureDetail(String lectureId);
	public List<AdminLectureChapterDomain> selectLectureChapter(String lectureId);
}