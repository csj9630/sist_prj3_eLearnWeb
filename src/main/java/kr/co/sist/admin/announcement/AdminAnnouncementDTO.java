package kr.co.sist.admin.announcement;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("announcementDTO")
@Getter
@Setter
@ToString
public class AdminAnnouncementDTO {
 
	private int ann_id, views;
	private String title, content, regip, adm_id; 
	private Date regdate;
	
}
