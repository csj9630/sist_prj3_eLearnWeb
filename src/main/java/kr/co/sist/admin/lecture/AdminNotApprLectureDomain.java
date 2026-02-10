package kr.co.sist.admin.lecture;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 강의 관리 테이블에 들어갈 도메인
 */
@Alias("notApprlectListDomain")

@Getter
@Setter
@ToString
public class AdminNotApprLectureDomain {
	private String thumbnail, lect_id, lect_name, inst_name; 
	private Date regDate;
	private int approval;
}
