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
	private String name;
	private int length;
	private Date regdate;
	private String doc;
	private String video;
	private int progress;
	private int state;

	// private String chptr_id ;
//	private String name, video, doc;
//	private int num, progTime, length;
//	private double videoProgress;
//	private Date regDate;
}
