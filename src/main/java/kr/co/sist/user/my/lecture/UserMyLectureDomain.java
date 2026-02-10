package kr.co.sist.user.my.lecture;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserMyLectureDomain {
    private String myLectId;    // 수강신청 ID
    private String userId;      // 사용자 ID
    private String lectId;      // 강의 ID
    private String lectName;    // 강의명
    private String thumbnail;   // 썸네일
    private String instName;    // 강사명
    private String shortIntro;  // 짧은 소개
    private Date regDate;       // 신청일
    
    // [추가] 진도율 관련
    private int totalChapter;   // 전체 챕터 수
    private int myChapter;      // 내가 수강한(생성된) 챕터 수
    private int progRate;       // 진도율 (%)
    
    private String intro;    // 강의 소개 (CLOB -> String)
    private int length;      // 총 영상 시간 (분)
    private String catName;  // 카테고리명
    private int userCount;   // 수강생 수
}