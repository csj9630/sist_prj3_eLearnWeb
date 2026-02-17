package kr.co.sist.user.lecture;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias(value="lectureDetailDomain")
@Data
public class LectureDetailDomain {
    private String lectId;//강의ID
    private String name;//강의명
    private int price; // 가격
    private String thumbnail; //썸네일 이미지
    private String description; // 강의 설명
    private String goal;        // 강의 목표
    private String instName;    // 강사명
    private String instProfile; // 강사 프로필 이미지/설명
}//class