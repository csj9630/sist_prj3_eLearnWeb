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
	private String stuId, chptrId ;
	private String title, videoUrl, prevVideoUrl, nextVideoUrl;
	int progTime, videoLength;
	List<String> videoList;
	//Date lastDate;
}
