package kr.co.sist.common.lecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class CommonLectureService {

//	@Autowired(required = false)
	@Autowired
	private CommonLectureMapper clm;
	
	public List<CategoryDomain> getAllCategories() {
        return clm.selectAllCategories();
    }

    public List<SkillDomain> getAllSkills() {
        return clm.selectAllSkills();
    }

	
}//class
