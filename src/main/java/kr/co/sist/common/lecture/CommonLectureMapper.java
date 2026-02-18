package kr.co.sist.common.lecture;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface CommonLectureMapper {
	public List<SkillDomain> selectAllSkills() ;
	public List<CategoryDomain> selectAllCategories() ;

}
