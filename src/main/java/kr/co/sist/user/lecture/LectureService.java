package kr.co.sist.user.lecture;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class LectureService {

//	@Autowired(required = false)
	@Autowired
	private LectureMapper lm;

	//메인화면용 강의리스트, 리뷰정보까지 조회
	public List<LectureDomain> searchChapterProgress() {
		List<LectureDomain> list = null;
		try { 
			list = lm.getLectureList();
		}catch(PersistenceException pe) {
			pe.printStackTrace();
		}
		return list;
	}//method
	
}//class
