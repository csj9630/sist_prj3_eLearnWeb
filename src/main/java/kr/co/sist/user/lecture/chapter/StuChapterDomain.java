package kr.co.sist.user.lecture.chapter;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 */
@EqualsAndHashCode(callSuper = true) //부모 필드까지 EqualsAndHashCode 생성
@Data
@Alias(value = "stuChapterDomain")
public class StuChapterDomain extends ChapterDomain {
	private String video;
	private int progress;
	private int state;
	
	// 타임리프에서 [[${item.stateStr}]] 로 호출 가능
    public String getStateStr() {
        if (this.state == 1) {
            return "시청 중";
        } else if (this.state == 2) {
            return "시청 완료";
        } else {
            return "시청 전";
        }
    }

}
