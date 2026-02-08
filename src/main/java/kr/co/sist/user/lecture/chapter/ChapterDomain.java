package kr.co.sist.user.lecture.chapter;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@ToString
@Data
@Alias(value = "chapterDomain")
public class ChapterDomain {
	private String chptrId ;
	private String name;
	private int length;
	private Date regdate;
	private String doc;
	private String video;
	private int progress;
	private int state;

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

	// private String chptr_id ;
//	private String name, video, doc;
//	private int num, progTime, length;
//	private double videoProgress;
//	private Date regDate;
}
