package kr.co.sist.user.lecture.test;


import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("stuAnswerDTO")
@Getter
@Setter
@ToString
public class StuAnswerDTO {
	//시험 문제 번호, 작성 답안.
	int test_q_num, choice;
}
