package kr.co.sist.admin.lecture;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("lectSearchDTO")

@Getter
@Setter
@ToString
public class AdminLectureSearchDTO {
	private String categoryName; //카테고리명
	private int size=5; //보여줄 개수(가데이터 부족으로 임시로 5개씩 보여주게 설계)
	private int page=1; //페이지 번호
	
	public int getOffset() {
		int p=page <= 0 ? 1: page;
		return (p-1)*size;
	}
}