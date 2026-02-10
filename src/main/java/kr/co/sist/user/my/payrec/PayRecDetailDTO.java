package kr.co.sist.user.my.payrec;

import java.sql.Date;

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
public class PayRecDetailDTO {
	String payrecId, payId, lectId, lectName, lectThumbnail, instName, instId;
	int lectPrice;
	Date time;
}
