package kr.co.sist.instructor.lecture.test;


import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("instTestDTO")
@Getter
@Setter
@ToString
public class InstTestDTO {
	////시험ID, 강의ID, 문제ID, 질문, 선택지1~4,정답, 해설, 시험 번호
	private String id, lectId, qid, content, opt1, opt2, opt3, opt4, ans, exp,instId;
	private int num;
}
