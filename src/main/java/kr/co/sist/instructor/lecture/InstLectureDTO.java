package kr.co.sist.instructor.lecture;

import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Alias("instLectureDTO")
@Data
public class InstLectureDTO {
	private String lectId;// 수정 시 필요
    private String name; // 강의명 (NAME)
    private String shortint;// 강의요약 소개 (SHORTINT)
    private String intro;   // 강의 상세 소개 (INTRO - CLOB)
    private String catId;   // 카테고리 (CAT_ID)
    private String catName;   // 카테고리명 (CAT_NAME)
    private String thumbnail;   // 썸네일 파일명 (THUMBNAIL)
    private int price;      // 강의 가격 (PRICE)
    private String instId;  // 강사 아이디 (세션에서 설정)
    private String regip;   // 등록 IP
    
    private String rejectReason;// 반려사유
    private List<String> skills; // 체크박스에서 선택된 스킬 ID 리스트
    private MultipartFile thumbFile; // 썸네일 파일 처리를 위한 객체
}
