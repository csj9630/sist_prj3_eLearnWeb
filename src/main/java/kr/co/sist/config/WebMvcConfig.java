package kr.co.sist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.lecture.img-path}")
    private String imgPath; // "/common/images/lecture_thumbs/**"

    @Value("${file.lecture.upload-path}")
    private String uploadPath; // "C:/upload/lecture_thumbs/"

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 1. 강의 썸네일 매핑 (중요: 슬래시 3개가 아니라 2개면 충분합니다)
        registry.addResourceHandler(imgPath + "**")
                .addResourceLocations("file:" + uploadPath);

        // 2. 프로필 이미지 매핑 (하드코딩된 C:/ 경로 삭제)
        // /images/** 요청이 오면 /upload/ 경로에서 찾도록 수정
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/upload/"); 
    }

}

