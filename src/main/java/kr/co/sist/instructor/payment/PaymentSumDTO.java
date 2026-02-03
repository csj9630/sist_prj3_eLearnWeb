package kr.co.sist.instructor.payment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentSumDTO {
	String lectId, lectName, instId, instName, month;
	int totalPrice, salesCount,userCount;
}
