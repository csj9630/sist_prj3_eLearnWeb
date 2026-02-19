package kr.co.sist.user.lecture.test;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//시험이력 테이블 데이터 삽입
@Alias("stuTestRecordDTO")
@Getter
@Setter
@ToString
public class StuTestRecordDTO {
	// 시험 ID, 학생 ID, 점수, 응시 ip
	private String id,userId, ip;
	private int score;
	private String my_test_id;
}
