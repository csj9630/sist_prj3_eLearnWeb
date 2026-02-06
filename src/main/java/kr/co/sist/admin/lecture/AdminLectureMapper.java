package kr.co.sist.admin.lecture;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminLectureMapper {

	public List<String> selectAllCategory();
	public int updateStatus(String lectureId);
	public List<ManageLectureDomain> selectLectureByCategory(ManageLectureSearchDTO mlsDTO);
	public List<ManageNotApprLectureDomain> selectNotApprLectList();
	
}