package kr.co.sist.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.sist.interceptor.AdminInterceptor;
import kr.co.sist.interceptor.InstInterceptor;
import kr.co.sist.interceptor.UserInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	
	@Value("${user.addPath}")
	private List<String> addPath;
	
	@Value("${user.excludePath}")
	private List<String> excludePath;
	
	@Value("${inst.addPath}")
	private List<String> addPathInst;
	
	@Value("${inst.excludePath}")
	private List<String> excludePathInst;
	
	@Value("${admin.addPath}")
	private List<String> addPathAdmin;
	
	@Value("${admin.excludePath}")
	private List<String> excludePathAdmin;
	
	@Autowired
	private UserInterceptor userInterceptor;
	
	@Autowired
	private InstInterceptor instInterceptor;
	
	@Autowired 
	private AdminInterceptor adminInterceptor;
	
	 
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//유저
		registry.addInterceptor(userInterceptor)//인터셉터 등록
		.addPathPatterns(addPath)//인터셉터터가 동작할 경로
		.excludePathPatterns(excludePath);//인터셉터가 동작하지 않을 경로 
		
		
		//강사 
		registry.addInterceptor(instInterceptor)//인터셉터 등록
		.addPathPatterns(addPathInst)//인터셉터터가 동작할 경로
		.excludePathPatterns(excludePathInst);//인터셉터가 동작하지 않을 경로
		
		//관리자 
		registry.addInterceptor(adminInterceptor)//인터셉터 등록
		.addPathPatterns(addPathAdmin)//인터셉터터가 동작할 경로
		.excludePathPatterns(excludePathAdmin);//인터셉터가 동작하지 않을 경로
		 		
	}
	
	
	
}
