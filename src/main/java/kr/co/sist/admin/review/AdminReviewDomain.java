package kr.co.sist.admin.review;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("adminReviewDomain")
@Getter
@Setter
@ToString
public class AdminReviewDomain {
	
	private String review_id, content, regip, lect_id, user_id, user_name, lecture_name;
	private int score;
	private Date regdate;
	
} // class
