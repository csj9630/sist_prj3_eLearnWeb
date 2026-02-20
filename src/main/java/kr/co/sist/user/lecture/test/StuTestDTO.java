package kr.co.sist.user.lecture.test;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//이 DTO 필요 없지 않나;;;
@Alias("stuTestDTO")
@Getter
@Setter
@ToString
public class StuTestDTO {
	//시험 id, 강의 id, 문제 id, 질문, 선택지 1~4, 정답, 해설
	String id, lectId, qid, content, opt1, opt2, opt3, opt4, exp;
	
	//문제 번호, 정답 번호
	int num, ans;

}
