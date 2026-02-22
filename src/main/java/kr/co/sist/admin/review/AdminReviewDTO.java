package kr.co.sist.admin.review;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("adminReviewDTO")
@Getter
@Setter
@ToString
public class AdminReviewDTO {
	
	private String review_id, content, regip, lect_id, user_id;
	private int score;
	private Date regdate;
	
} // class
