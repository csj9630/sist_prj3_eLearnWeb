package kr.co.sist.common.question;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("commonQuestionDTO")
@Getter
@Setter
@ToString
public class CommonQuestionDTO {
	
	private int		views;
	private String	question_id, lect_id, user_id, title, content, regip, ans, ansip; 
	private Date	regdate, ansdate;
	
}
