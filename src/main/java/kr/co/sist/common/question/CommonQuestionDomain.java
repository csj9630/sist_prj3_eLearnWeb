package kr.co.sist.common.question;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("commonQuestionDomain")
@Getter
@Setter
@ToString
public class CommonQuestionDomain {
	
	private int		views;
	private String	question_id, lect_id, user_id, title, content, q_regip, ans, ansip, user_name, lect_name, inst_id, inst_name; 
	private Date	q_regdate, ansdate;
	
}
