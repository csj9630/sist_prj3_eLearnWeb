package kr.co.sist.user.lecture.chapter;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service 
public class ChapterService {

//	@Autowired(required = false)
	@Autowired
	private ChapterMapper cm;

	/**
	 * 강의 상세용 커리큘럼 조회
	 * @param lectId
	 * @return
	 */
	public List<ChapterDomain> searchChapterList(String lectId) {
		List<ChapterDomain> list = null;
		//cdto = new ChapterDTO("user1","L1");
		try { 
			list = cm.selectChapterList(lectId);
		}catch(PersistenceException pe) {
			pe.printStackTrace();
		}
		
		return list;
	}//method
	
	/**
	 * 수강 중인 강의 커리큘럼 및 진척도 조회
	 * @param cdto
	 * @return
	 */
	public List<StuChapterDomain> searchChapterProgress(ChapterDTO cdto) {
		List<StuChapterDomain> list = null;
		//cdto = new ChapterDTO("user1","L1");
		try { 
			list = cm.selectChapterProgress(cdto);
		}catch(PersistenceException pe) {
			pe.printStackTrace();
		}
		
		return list;
	}//method
	
	public VideoDomain0 getVideoInfo(String stuId, String chptrId) {
		VideoDomain0 vd = new VideoDomain0();
		
		vd.setStuId(stuId);
		vd.setChptrId(chptrId);
		vd.setTitle("유튜브 영상 제목");
		vd.setVideoUrl("cg1xvFy1JQQ");
		vd.setPrevVideoUrl("4VR-6AS0-l4");
		vd.setNextVideoUrl("mNlxH0a6CfI");
		vd.setProgTime(35);
		
		return vd;
	}//method
	
	
	public List<VideoDomain> getVideoInfoList(	ChapterDTO cdto) {
		List<VideoDomain> vdList = null;
		
		try { 
			vdList = cm.selectVideoData(cdto);
		}catch(PersistenceException pe) {
			pe.printStackTrace();
		}
		return vdList;
	}//method
	
	//@Transactional // Service 메서드 안의 모든 쿼리가 성공 시 커밋, 하나라도 실패 시 rollBack;
	public boolean saveVideoRecord(VideoDTO vdto) {
		boolean flag =false;
		try {
			flag = cm.mergeRecordtoMyChapter(vdto) ==1;
		} catch (Exception e) {
			e.printStackTrace();
		} // end catch
		return flag;
	}//method
}//class
