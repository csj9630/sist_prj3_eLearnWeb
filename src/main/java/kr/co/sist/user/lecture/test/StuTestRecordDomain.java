package kr.co.sist.user.lecture.test;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("stuTestRecordDomain")
@Getter
@Setter
@ToString
public class StuTestRecordDomain {
	//시험이력 ID, 시험 ID, 학생 ID, 응시 IP, 문제 ID, 선택지 1~4, 해설
	String id, testId, stuId, ip, lectId, qid, content, opt1, opt2, opt3, opt4, exp;
	
	//시험 점수, 작성 답안 번호, 정답 번호
	int score, choice, ans;
	
	//응시 상태
	boolean state;
	
	//응시 날짜
	Date date;
}
