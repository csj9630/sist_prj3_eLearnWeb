package kr.co.sist;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @MapperScan("kr.co.sist.user.lecture.chapter")
public class SistPrj3ELearnWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistPrj3ELearnWebApplication.class, args);
	}

}
