package kr.co.sist.admin.lecture;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("lectDetailDomain")

@Getter
@Setter
@ToString
public class AdminLectureDetailDomain {
	private String lect_id, lect_name, thumbnail, category_name, inst_name, intro;
	private int price, user_count, availability, approval;
	private Date regDate;
}