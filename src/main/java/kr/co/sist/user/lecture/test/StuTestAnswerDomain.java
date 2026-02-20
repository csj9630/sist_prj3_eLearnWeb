package kr.co.sist.user.lecture.test;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 사용자가 입력한 결과와 정답 비교 후 해설 알려줄 용도의 Domain
@Alias("stuTestAnswerDomain")
@Getter
@Setter
@ToString
public class StuTestAnswerDomain {
	//시험 id, 강의 id, 문제 id, 정답, 해설, 문제번호
	private String id, lectId, qid, exp;
	
	//문제 번호, 정답 번호
	private int num, ans;
}
