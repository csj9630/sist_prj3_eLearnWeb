package kr.co.sist.user.lecture.test;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Alias("stuCheckTestDTO")
@Getter
@Setter
@ToString
//유저 시험 유무 체크 DTO
public class StuCheckTestDTO {
	private String userId, testId, lectId;
}
