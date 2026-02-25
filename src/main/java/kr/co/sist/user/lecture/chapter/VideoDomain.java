	package kr.co.sist.user.lecture.chapter;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Alias("videoDomain")
public class VideoDomain {
	private String userId, chptrId ;
	private String title, videoUrl;
	int num, progTime, videoLength, progress, state, actualTime;
	
	
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
	public String getProgTimeStr() {
		return convertHms(this.progTime);
	}//method
	
	/**
	 * 영상 시간 등을 초에서 시분초 문자열로 변환
	 * 타임리프에서 videoLengthStr로 호출 가능.
	 * @return
	 */
	public String getVideoLengthStr() {
		return convertHms(this.videoLength);
	}//method


	//List<String> videoList;
	//Date lastDate;
}
