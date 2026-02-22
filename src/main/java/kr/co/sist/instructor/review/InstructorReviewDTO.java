package kr.co.sist.instructor.review;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("instReviewDTO")
@Getter
@Setter
@ToString
public class InstructorReviewDTO {
	
	private String review_id, content, regip, lect_id, user_id;
	private int score;
	private Date regdate;
	
} // class
