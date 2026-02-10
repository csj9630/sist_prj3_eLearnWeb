package kr.co.sist.admin.lecture;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminLectureMapper {

	public List<String> selectAllCategory();
	public int updateStatus(String lectureId);
	//강의 승인 
	public int updateApproval(String lectureId);
	public int updateAvailability(String lectureId, int availability);

	//	<select id="selectLectureByCategory" resultType="lectListDomain" parameterType="lectSearchDTO">
	public List<AdminLectureDomain> selectLectureByCategory(AdminLectureSearchDTO alsDTO);
	public List<AdminNotApprLectureDomain> selectNotApprLectList(AdminLectureSearchDTO alsDTO);
	public List<AdminLectureDetailDomain> selectLectureDetail(String lectureId);
	public List<AdminLectureChapterDomain> selectLectureChapter(String lectureId);
}