package kr.co.sist.user.lecture.board;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("skillDomain")
@Data
public class SkillDomain {
	String skillId, name;
}
