package kr.co.sist.user.lecture.detail;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("lectureDetailDomain")
@Data
public class LectureDetailDomain {
    private String lectId;//강의ID
    private String name;//강의명
    private int price; // 가격
    private String thumbnail; //썸네일 이미지
    private String shortint; // 짧은 소개
    private String intro; // 상세 소개 (CLOB -> String)
    private String catName; // 카테고리명
    private int count;// 강의 수
    private int length;// 총 시간
    private String instName;    // 강사명 (JOIN으로 가져올 것)
}//class