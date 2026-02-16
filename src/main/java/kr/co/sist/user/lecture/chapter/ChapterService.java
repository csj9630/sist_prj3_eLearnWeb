package kr.co.sist.user.lecture.chapter;

import java.util.List;
import java.util.Map;

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
	

    /**
     * 챕터 ID로 파일 정보 조회
     */
    public FileDomain getFileInfo(String chptrId) {
        FileDomain fileDomain = cm.selectFileInfo(chptrId);
        
        // 데이터가 없거나 파일명이 비어있는 경우 예외 처리 가능
        if (fileDomain == null || fileDomain.getDoc() == null) {
            throw new RuntimeException("해당 챕터에 등록된 자료가 없습니다. ID: " + chptrId);
        }
        
        return fileDomain;
	}// method
    
    //모든 영상 상태가 시청완료인지 체크.
    public boolean isExamReady(List<StuChapterDomain> list) {
    	if (list == null || list.isEmpty()) return false;
        
        // 모든 챕터의 state가 2인지 확인 (Java 8 스트림 활용)
        return list.stream().allMatch(chapter -> "2".equals(chapter.getState()));
	}// method
    
    //최신 시험 점수 가져옴.
    public Integer getLatestScore(String userId, String lectId) {
        // 최신 응시 기록이 없으면 MyBatis는 null을 반환합니다.
        return cm.selectLatestTestScore(userId, lectId);
    }

}// class
