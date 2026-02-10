package kr.co.sist.user.lecture.test;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("stuTestRecordDTO")
@Getter
@Setter
@ToString
public class StuTestRecordDTO {
	//시험이력 ID, 시험 ID, 학생 ID, 응시 ip
	String id, stuId, ip;
}
