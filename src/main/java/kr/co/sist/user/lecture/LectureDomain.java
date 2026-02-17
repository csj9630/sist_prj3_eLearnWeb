package kr.co.sist.user.lecture;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("mainLectureDomain")
@NoArgsConstructor
@Data
public class LectureDomain {
	private String lectId;
    private String name;
    private String instName;
//    private String instId;
    private String thumbnail;
    private int price;
    private int userCount;
    
    // 리뷰 통계 필드
    private int reviewCount;  
    private double avgScore;  
}
