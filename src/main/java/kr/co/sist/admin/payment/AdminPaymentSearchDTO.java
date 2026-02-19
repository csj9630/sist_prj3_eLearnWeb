package kr.co.sist.admin.payment;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("paymentSearchDTO")

@Getter
@Setter
@ToString
public class AdminPaymentSearchDTO {
	private String instName;
}