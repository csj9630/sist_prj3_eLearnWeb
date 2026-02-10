package kr.co.sist.admin.lecture;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("lectSearchDTO")

@Getter
@Setter
@ToString
public class ManageLectureSearchDTO {
	private String categoryName;
}