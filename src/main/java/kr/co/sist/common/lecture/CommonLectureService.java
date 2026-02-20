package kr.co.sist.common.lecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class CommonLectureService {

//	@Autowired(required = false)
	@Autowired
	private CommonLectureMapper clm;
	
	//강의 검색용 카테고리 목록(id,카데고리명)을 가져온다.
	public List<CategoryDomain> getAllCategories() {
        return clm.selectAllCategories();
    }

	//강의 스킬 리스트 목록을 가져온다. 아이디 없이 이름만 중복 제외하고 조회한다
    public List<SkillDomain> getAllSkills() {
        return clm.selectAllSkills();
    }
    
    //학생id, 강의id를 받아서 학생이 강의 수강 중인지 체크한다.
    public boolean isMyLecture(String userId, String lectId) {
    	return clm.selectMyLectureCount(userId, lectId) >0;
    }

	
}//class
