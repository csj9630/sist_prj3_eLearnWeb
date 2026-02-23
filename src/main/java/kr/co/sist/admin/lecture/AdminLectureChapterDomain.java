package kr.co.sist.admin.lecture;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("lectChapterDomain")

@Getter
@Setter
@ToString
public class AdminLectureChapterDomain {
	private int ch_num, ch_length;
	private String ch_id, ch_name, ch_video, ch_doc;
	private Date ch_regdate;
	
	/** 초 시간을 '시:분:초'의 문자열로 변환.
	 * @param second
	 * @return
	 */
	public String convertHms(int second) {
		int h = second / 3600;
		int m = (second % 3600) / 60;
		int s = second % 60;

		if (h > 0) {
			return String.format("%d:%02d:%02d", h, m, s);
		} else {
			return String.format("%d:%02d", m, s);
		}
	}//method
	/**
	 * 영상 시간 등을 초에서 시분초 문자열로 변환
	 * 타임리프에서 progTimeStr로 호출 가능.
	 * @return
	 */
	public String getCh_lengthStr() {
		return convertHms(this.ch_length);
	}//method
}
