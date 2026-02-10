package kr.co.sist.admin.payment;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("lectProfitDomain")

@Getter
@Setter
@ToString
public class LectProfitDomain {
	private Date time;
	private String lect_name, inst_name;
	private int inst_profit;
}
