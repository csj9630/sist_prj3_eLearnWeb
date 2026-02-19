package kr.co.sist.instructor.dashboard;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("instDashboardDomain")

@Getter
@Setter
@ToString
public class InstructorDashBoardDomain {
	private String lect_id, lect_name;
	private int estimated_profit, user_count;
}
