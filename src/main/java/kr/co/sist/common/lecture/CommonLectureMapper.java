package kr.co.sist.common.lecture;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface CommonLectureMapper {
	public List<SkillDomain> selectAllSkills(@Param("lectId") String lectId) ; //	<!--검색 필드용 카테고리 조회-->
	public List<CategoryDomain> selectAllCategories() ; //검색 핃드용 스킬 조회, 아이디 없이 이름만 중복 제외하고 조회한다.
	public int selectMyLectureCount( @Param("userId")String userId,@Param("lectId")String lectId ) ;
}
