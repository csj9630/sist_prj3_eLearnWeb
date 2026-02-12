package kr.co.sist.instructor.lecture;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstLectureService {

	@Autowired
	private InstLectureMapper im;
	public void testtest() {
		System.out.println("junit 테스트");
	}
	
	/**
	 * 	사용처 : 강사 강의 관리 - 강의리스트
	 * @param instId(강사id)
	 * @return List<InstLectureDomain>
	 */
	public List<InstLectureDomain> searchInstLectureList(String instId) {
		List<InstLectureDomain> list = null;
		try { 
			list = im.selectInstLectureList(instId);
		}catch(PersistenceException pe) {
			pe.printStackTrace();
		}
		
		return list;
	}//method
}//class
