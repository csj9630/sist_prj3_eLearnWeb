package kr.co.sist.user.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MyCartDTO {
	String cartId, userId, lectId, lectName, lectThumbnail, instname, instId;
	int price;
}
