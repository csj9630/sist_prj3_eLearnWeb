package kr.co.sist.instructor.lecture;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias(value="instLectureDomain")
public class InstLectureDomain {
	private String thumbnail;
	private String lectId;
	private String instId;
	private String instName;
	private String name;
	private String catId;
	private String catName;
	private double reviewAvg;
	private int userCount;      
    private int questCnt;
    private int cash;           // 총 수입
    private int availability;   // 공개/비공개 여부
    
    private String approvalStatus; // "승인", "거절", "대기"
    private String rejectReason;   // 거절 사유
}//class
