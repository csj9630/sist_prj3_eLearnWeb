package kr.co.sist.admin.announcement;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Alias("announcementDomain")
@Getter
@Setter
@ToString
public class AdminAnnouncementDomain {
 
	private int ann_id, views;
	private String title, content, regip, adm_id; 
	private Date regdate;
	
}
