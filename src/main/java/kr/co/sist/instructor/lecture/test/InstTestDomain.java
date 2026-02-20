package kr.co.sist.instructor.lecture.test;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 시험 관리 페이지 -> 제작된 문제 목록 페이지 Domain ( 문제의 질문 내용만 필요 ) 
 */
@Alias("instTestDomain")
@Getter
@Setter
@ToString
public class InstTestDomain {
	//시험ID, 강의ID, 문제ID, 질문, 선택지1~4,정답, 해설
	private String id, lectId, qid, content,opt1, opt2, opt3, opt4, ans, exp;
	private int num;
	
}
