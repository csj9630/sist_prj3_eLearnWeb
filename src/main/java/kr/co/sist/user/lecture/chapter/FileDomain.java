package kr.co.sist.user.lecture.chapter;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias (value = "FileDomain")
public class FileDomain {
    private String chptrId;  // 챕터 ID
    private String doc;      // DB에 저장된 파일명 (예: lect.ppt)
}