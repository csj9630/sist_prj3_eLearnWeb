package kr.co.sist.instructor.dashboard;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("instDashboardDomain")

@Getter
@Setter
@ToString
public class InstructorDashBoardDomain {
	private String lect_name;
	private int total_revenue, user_count;
	private Date pay_date;
}
