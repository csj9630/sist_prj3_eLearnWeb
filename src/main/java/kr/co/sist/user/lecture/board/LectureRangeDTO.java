package kr.co.sist.user.lecture.board;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("lectureRangeDTO")
@Getter
@Setter
@ToString
public class LectureRangeDTO {
	private int startNum, endNum;
	private String field, keyword;
	private String fieldStr;
	private String url = "/user/lecture/lectureList"; // 기본 URL 설정
	
	//검색용 필드
	private String catId; 
	private String skillName; //태그처럼 써서 id 말고 이름으로 조회.
	
	private int currentPage = 1;
	private int totalPage = 0;

	public String getFieldStr() {
		// 1: 강의명(name), 2: 강사명(inst_name) 등  테이블 컬럼에 맞춰라
		String[] fieldTitle = { "l.name", "i.name" };
		int tempField = (field == null || field.isEmpty()) ? 0 : Integer.parseInt(field) - 1;
		return fieldTitle[tempField >= 0 && tempField < 2 ? tempField : 0];
	}
}