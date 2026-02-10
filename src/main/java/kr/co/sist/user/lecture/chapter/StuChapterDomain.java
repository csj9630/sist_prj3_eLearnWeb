package kr.co.sist.user.lecture.chapter;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 */
@EqualsAndHashCode(callSuper = true) //부모 필드까지 EqualsAndHashCode 생성
@Data
@Alias(value = "stuChapterDomain")
public class StuChapterDomain extends ChapterDomain {
	private String video;
	private int progress;
	private int state;

}
