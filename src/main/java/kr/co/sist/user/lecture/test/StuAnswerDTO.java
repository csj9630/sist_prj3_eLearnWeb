package kr.co.sist.user.lecture.test;


import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//시험 응시 테이블
@Alias("stuAnswerDTO")
@Getter
@Setter
@ToString
public class StuAnswerDTO {
	//시험 문제 번호, 작성 답안, 시험이력 아이디
	private List<Integer> test_q_num;
	private String my_test_id;
	private List<String> qid;
	private List<Integer> choice;
}
