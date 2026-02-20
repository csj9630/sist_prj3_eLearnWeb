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

        // 강의 썸네일: "/common/images/lecture_thumbs/**" → "C:/upload/lecture_thumbs/"
        // properties에 정의된 가상 경로와 물리 경로를 그대로 연결
        registry.addResourceHandler(imgPath + "**")
                .addResourceLocations("file:///" + uploadPath);

        // 프로필 이미지: "/images/**" → "C:/upload/"
        // DB에 저장된 경로 예시: /images/profile/kim/img_1.jpg
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///C:/upload/");
    }
}
