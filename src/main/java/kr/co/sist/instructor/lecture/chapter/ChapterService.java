package kr.co.sist.instructor.lecture.chapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class ChapterService {

	@Autowired
	private ChapterDAO cDAO;
}//class
