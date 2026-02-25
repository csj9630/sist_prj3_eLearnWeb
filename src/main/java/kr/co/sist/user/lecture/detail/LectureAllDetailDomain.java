package kr.co.sist.user.lecture.detail;

import java.util.List;

import kr.co.sist.user.lecture.chapter.ChapterDomain;
import kr.co.sist.user.lecture.review.UserReviewDomain;
import lombok.Data;

//강의 상세 페이지 관련 정보 종합하는 DTO

@Data
public class LectureAllDetailDomain {
	private LectureDetailDomain detail;
    private List<ChapterDomain> chapter;
    private List<UserReviewDomain> review;
}
