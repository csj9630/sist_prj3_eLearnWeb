package kr.co.sist.common.announcement;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("commonAnnouncementDTO")
@Getter
@Setter
@ToString
public class CommonAnnouncementDTO {
	
	private int		ann_id, views;
	private String	title, content, regip, adm_id; 
	private Date	regdate;
	
}
