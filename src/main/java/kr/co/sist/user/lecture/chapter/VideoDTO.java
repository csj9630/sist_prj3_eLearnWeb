package kr.co.sist.user.lecture.chapter;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // 매칭 안되는 필드는 무시
@ToString
@Alias("videoDTO")
public class VideoDTO {
	@Setter
	private String userId, chptrId;

	// state를 계산하기 위해 setter를 custom
	private Integer progTime; // 시청 시간
	private Integer videoLength; // 전체 영상 길이
	private Integer actualTime; // 실제 영상 시청 시간.

	// 이 둘은 setter 없이 계산되어서 저장됨.
	private Integer progress; // 진행률 (%)
	private Integer state; // 상태 (0:미시청, 1:시청중, 2:완료)

	// progTime이 바뀔 때마다 progress,state를 계산
	@JsonProperty("progTime")
	public void setProgTime(int progTime) {
		this.progTime = progTime;
		//this.recalculate();
	}// method

	// videoLength가 바뀔 때마다 progress, state를 계산
	@JsonProperty("videoLength")
	public void setVideoLength(int videoLength) {
		this.videoLength = videoLength;
		this.recalculate();
	}// method

	// 실제 시청 시간이 바뀔 때마다 progress, state를 계산 -> 진도율 갱신
	@JsonProperty("actualTime")
    public void setActualTime(Integer actualTime) {
        this.actualTime = actualTime;
        this.recalculate(); 
    }
	/**
	 * 진행률과 상태를 재계산하는 내부 메서드
	 */
	public void recalculate() {
		//videoLength,progTime 하나라도 0이거나 null이면 early return.
		if (this.videoLength == null || this.actualTime == null || this.videoLength <= 0) {
			this.progress = 0;
			this.state = 0;
			return;
		}

		// 0으로 나누기 방지
		if (this.videoLength <= 0) {
			this.progress = 0;
			this.state = 0;
			return;
		}

		// 1. 진행률 계산 (소수점 버림)
		this.progress = (int) ((long) this.actualTime * 100 / this.videoLength);

		// 진행률이 100을 넘지 않게 함
		if (this.progress > 100) {
			this.progress = 100;
		} // if

		// 2. 상태(state) 결정 : 95% 이상이면 완료 처리
		if (this.state != null && this.state == 2) {
	        // 이미 완료된 강의이므로 state는 2 고정
	        // 단, 시청 시간(progTime)은 0부터 다시 시작하더라도 progress는 100 유지
		} else if (this.progress >= 95 || this.actualTime >= this.videoLength) {
			this.state = 2; // 완료
			//this.progress=0;//처음으로 돌아가기.
		} else if (this.progress > 0 || this.actualTime > 0 || this.progTime > 0) {
			this.state = 1; // 시청 중
		} else {
			this.state = 0; // 시작 전
		} // else

	}

}// class
