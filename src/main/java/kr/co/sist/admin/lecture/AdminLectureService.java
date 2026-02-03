package kr.co.sist.admin.lecture;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 강의에 해당하는 교육 과목 관리, 강의 관리 페이지 담당
 */
@Service
public class AdminLectureService {

	@Autowired(required=false)
	private AdminLectureMapper alm;
	
	public List<ManageLectureDomain> searchLectureList() throws PersistenceException{
		List<ManageLectureDomain> lectureList;
		lectureList=alm.selectLectList();
		
		return lectureList;
	}//searchLectureList
	
	public List<ManageNotApprLectureDomain> searchNotApprLectList() throws PersistenceException{
		List<ManageNotApprLectureDomain> notApprLectureList;
		notApprLectureList=alm.selectNotApprLectList();
		
		return notApprLectureList;
	}//searchNotApprLectList
}
