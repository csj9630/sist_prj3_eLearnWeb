package kr.co.sist.user.lecture.test;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//사용자 시험 페이지 view에 데이터 뿌려주기용 Domain
@Alias("stuTestViewDomain")
@Getter
@Setter
@ToString
public class StuTestViewDomain {
	//시험 id, 강의 id, 문제 id, 질문, 선택지 1~4, 정답,  문제번호, 강의 이름
	private String id, lectId, qid, content, opt1, opt2, opt3, opt4,  lectName;
	
	//문제 번호
	private int num;
}
