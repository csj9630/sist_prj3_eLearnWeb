package kr.co.sist.user.lecture.chapter;

import java.sql.Date;

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
public class ChapterDomain {
	private String chptr_id ;
	private String name, video, doc;
	private int num, progTime, length;
	private double videoProgress;
	private Date regDate;
}
