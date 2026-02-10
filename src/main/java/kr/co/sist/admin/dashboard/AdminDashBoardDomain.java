package kr.co.sist.admin.dashboard;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("dashboardDomain")

@Getter
@Setter
@ToString
public class AdminDashBoardDomain {
	private String lect_id, lect_name;
	private int estimated_profit, user_count;
}
