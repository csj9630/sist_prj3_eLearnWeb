package kr.co.sist.user.lecture.chapter;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ChapterService {

//	@Autowired(required = false)
	@Autowired
	private ChapterMapper cm;

	//로그 객체
	private static final Logger log = LoggerFactory.getLogger(ChapterService.class);
	/**
	 * 강의 상세용 커리큘럼 조회
	 * 
	 * @param lectId
	 * @return
	 */
	public List<ChapterDomain> searchChapterList(String lectId) {
		List<ChapterDomain> list = null;
		// cdto = new ChapterDTO("user1","L1");
		try {
			list = cm.selectChapterList(lectId);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}

		return list;
	}// method

	/**
	 * 수강 중인 강의 커리큘럼 및 진척도 조회
	 * 
	 * @param cdto
	 * @return
	 */
	public List<StuChapterDomain> searchChapterProgress(ChapterDTO cdto) {
		List<StuChapterDomain> list = null;
		// cdto = new ChapterDTO("user1","L1");
		try {
			list = cm.selectChapterProgress(cdto);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}

		return list;
	}// method

	public VideoDomain0 getVideoInfo(String userId, String chptrId) {
		VideoDomain0 vd = new VideoDomain0();

		vd.setUserId(userId);
		vd.setChptrId(chptrId);
		vd.setTitle("유튜브 영상 제목");
		vd.setVideoUrl("cg1xvFy1JQQ");
		vd.setPrevVideoUrl("4VR-6AS0-l4");
		vd.setNextVideoUrl("mNlxH0a6CfI");
		vd.setProgTime(35);

		return vd;
	}// method

	public List<VideoDomain> getVideoInfoList(ChapterDTO cdto) {
		List<VideoDomain> vdList = null;

		try {
			vdList = cm.selectVideoData(cdto);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}
		return vdList;
	}// method

	// @Transactional // Service 메서드 안의 모든 쿼리가 성공 시 커밋, 하나라도 실패 시 rollBack;
	public boolean saveVideoRecord(VideoDTO vdto) {
		boolean flag = false;
		try {
			flag = cm.mergeRecordtoMyChapter(vdto) == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} // end catch
		return flag;
	}// method

	/**
	 * 학생 id를 받아서 강의 영상을 시청할 때 출석체크한다.
	 *
	 * @param userId
	 * @return
	 */
	public void checkAttendance(String userId) {
		boolean flag = false;
		try {
			flag = cm.insertStuAttendence(userId) == 1;
		} catch (DataIntegrityViolationException e) {
			log.error("DB 접속 에러 발생- 무결성 제약조건 : {}", e.getMessage());
			throw new RuntimeException("유효하지 않는 회원정보입니다.(type : ORA-0229)");

		} catch (DataAccessException e) {
			// 실무 포인트 2: DB 연결 끊김 등 인프라 에러
			log.error("DB 접속 에러 발생 : {}", e.getMessage());
			throw new RuntimeException("DB 서비스 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("출석 시스템 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
		} // end catch
		if (!flag) {
			throw new RuntimeException("출석 체크에 실패했습니다. 다시 시도해주세요.");
		}

		log.info("출석 체크됨 - 사용자 ID: {}", userId);
	}// method

}// class
