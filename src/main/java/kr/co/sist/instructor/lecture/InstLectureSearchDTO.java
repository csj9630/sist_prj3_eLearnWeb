package kr.co.sist.instructor.lecture;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("instLectureSearchDTO")
public class InstLectureSearchDTO {
    private String instId;      //  강사 ID
    private String keyword="";     // 강의명 검색어, 타임리프에서 에러 안나게 기본은 null이 아닌 공백으로.
    
    //검색 조건에서는 number라도  String으로 받아야 공백 처리에 유리함.
    private String availability=""; // "1"(공개), "0"(비공개), ""(전체)
    private String approval="";     // "0"(대기), "1"(승인), "2"(반려), ""(전체)
}