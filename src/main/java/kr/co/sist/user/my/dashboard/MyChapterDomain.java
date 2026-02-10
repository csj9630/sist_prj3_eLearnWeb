package kr.co.sist.user.my.dashboard;

import java.sql.Date;
import org.apache.ibatis.type.Alias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("myChapterDomain")
public class MyChapterDomain {
    private String myChapterId, chptrId, userId;
    private Date lastDate;
    private int progress, progtime, state;
    
    // [추가] 화면 표시용 변수
    private String lectName;
    private String chptrName;
    private String thumbnail;
}