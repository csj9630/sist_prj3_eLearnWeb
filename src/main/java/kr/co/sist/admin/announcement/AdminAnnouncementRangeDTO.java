package kr.co.sist.admin.announcement;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("adminAnnouncementRangeDTO")
@Getter
@Setter
@ToString
public class AdminAnnouncementRangeDTO {
	
	private int startNum, endNum; // 시작 번호, 끝 번호
	private String field, keyword; // 검색 필드(1, 2, 3), 검색 키워드
	private String fieldStr; // 검색 필드 값에 대응되는 컬럼명의 문자열
	private String url; // 이동할 URL
	private int currentPage = 1; // 현재 페이지
	private int totalPage = 0; // 총 페이지
	
	public String getFieldStr() {
		String[] fieldTitle = {"title", "content", "adm_id"};
		int tempField = Integer.parseInt(field);
		if(!(tempField > 0 && tempField < 4)) { // 1 ~ 3 사이가 아닌 경우
			tempField = 1;
		}
		fieldStr = fieldTitle[Integer.parseInt(field) - 1];
		return fieldStr;
	} // getFieldStr
	
}
