package kr.co.sist.common.lecture;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("categoryDomain")
@Data
public class CategoryDomain {
	String catId, name;
}
