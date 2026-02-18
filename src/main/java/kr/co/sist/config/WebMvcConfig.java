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
//        // /images/** 요청이 들어오면 로컬 C:/upload/ 폴더에서 찾음
//        registry.addResourceHandler("/images/**")
//                .addResourceLocations("file:///C:/upload/");
//        
        // "/common/images/lecture_thumbs/**" 로 들어오는 요청은
        // "file:///C:/upload/lecture_thumbs/"에서 찾기
    	// properties에 정의된 가상 경로와 물리 경로를 그대로 연결
        registry.addResourceHandler(imgPath + "**")
                .addResourceLocations("file:///" + uploadPath);
    }
}
