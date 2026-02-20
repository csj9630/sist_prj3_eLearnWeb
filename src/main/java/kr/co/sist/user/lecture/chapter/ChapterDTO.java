package kr.co.sist.user.lecture.chapter;

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
@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value = "chapterDTO")
public class ChapterDTO {
	private String userId, lectId;
}
