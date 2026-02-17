package kr.co.sist.user.lecture.board;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("categoryDomain")
@Data
public class CategoryDomain {
	String catId, name;
}
