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

	public List<ChapterDomain> searchChapterProgress(ChapterDTO cdto) {
		List<ChapterDomain> list = null;
		//cdto = new ChapterDTO("user1","L1");
		try { 
			list = cm.selectChapterProgress(cdto);
			System.out.println("=====================================");
			System.out.println(list);
			System.out.println("=====================================");
		}catch(PersistenceException pe) {
			pe.printStackTrace();
		}
		
		return list;
	}//method
	
	public VideoDomain getVideoInfo(String stuId, String chptrId) {
		VideoDomain vd = new VideoDomain();
		
		vd.setStuId(stuId);
		vd.setChptrId(chptrId);
		vd.setTitle("유튜브 영상 제목");
		vd.setVideoUrl("cg1xvFy1JQQ");
		vd.setPrevVideoUrl("4VR-6AS0-l4");
		vd.setNextVideoUrl("mNlxH0a6CfI");
		vd.setProgTime(35);
		
		return vd;
	}//method
	
}//class
