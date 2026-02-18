package kr.co.sist;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.sist.instructor.lecture.InstLectureService;
import lombok.extern.slf4j.Slf4j;
@Slf4j // 로그를 편하게 찍게 해줍니다
@SpringBootTest
class SistPrj3ELearnWebApplicationTests {

	@Autowired
	InstLectureService is;
	
	@Test
	void contextLoads() {
		String instId = "inst1";
		//var result = is.searchInstLectureList(instId);
		//log.info("결과 데이터: {}", result);
	}

}
