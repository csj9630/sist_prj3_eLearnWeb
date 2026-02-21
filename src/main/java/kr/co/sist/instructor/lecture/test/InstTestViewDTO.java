package kr.co.sist.instructor.lecture.test;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 강사 - 시험관리 페이지 확인 시 사용될 변수(강사id 및 강의 id) 저장 DTO 
 */
@Alias("instTestViewDTO")
@Getter
@Setter
@ToString
public class InstTestViewDTO {
	private String instId, lectId, qid;
}
