package kr.co.sist.instructor.lecture.chapter;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias(value = "instChapterDTO")
public class InstChapterDTO {
	private String chptrId ;
	private String lectId ;
	private String name; //챕터명
	private int length; //영상길이
	private Date regdate;
	private String doc;//문서
	private String video;//유튜브 링크.
	private int num;//순서.

	/**
	 * 영상시간 length을 초 -> (시:)분:초로 변환.
	 * ex)3725 -> 1:02:05
	 * ex)1204-> 5:40
	 * lengthStr로 타임리프에서 호출 가능.
	 * 
	 * @return String (시:)분:초
	 */
	public String getLengthStr() {
		int h = this.length / 3600;
		int m = (this.length % 3600) / 60;
		int s = this.length % 60;

		if (h > 0) {
			return String.format("%d:%02d:%02d", h, m, s);
		} else {
			return String.format("%d:%02d", m, s);
		}
	}

}
