package kr.co.sist.user.lecture.chapter;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class ChapterService {

//	@Autowired(required = false)
	@Autowired
	private ChapterMapper cm;

	public List<ChapterDomain> searchAllChapter(/* ChapterDTO cdto */) {
		List<ChapterDomain> list = null;
		//cdto = new ChapterDTO("stu001","chptr001");
		try { 
			list = cm.selectAllChapter();
			System.out.println("=====================================");
			System.out.println(list);
			System.out.println("=====================================");
		}catch(PersistenceException pe) {
			pe.printStackTrace();
		}
		
		return list;
	}//method
}//class
