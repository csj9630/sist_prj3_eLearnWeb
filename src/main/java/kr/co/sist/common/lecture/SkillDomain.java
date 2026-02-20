package kr.co.sist.common.lecture;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("skillDomain")
@Data
public class SkillDomain {
	String skillId, name;
}
