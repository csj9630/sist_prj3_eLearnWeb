package kr.co.sist.user.lecture.chapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class ChapterService {

	@Autowired
	private ChapterDAO cDAO;
	
	public void watchVideo(ChapterDTO cdto) {
		cdto = new ChapterDTO("stu001","chptr001");
		
	}
}//class
