package kr.co.sist.user.my.lecture;

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
public class UserMyLectureDTO {
	String id, chapterId, userId;
	Date lastDate;
	int progress, progTime, state;
}
