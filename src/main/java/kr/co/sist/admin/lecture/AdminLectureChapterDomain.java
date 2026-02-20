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
	private String ch_name, ch_video, ch_doc;
	private Date ch_regdate;
}
