package kr.co.sist.user.lecture.review;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("userReviewDomain")
@Getter
@Setter
@ToString
public class UserReviewDomain {
	
	private String review_id, content, regip, lect_id, user_id, name, profile;
	private int score;
	private Date regdate;
	
} // class
