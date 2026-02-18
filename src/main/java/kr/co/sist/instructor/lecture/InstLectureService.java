package kr.co.sist.instructor.lecture;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class InstLectureService {

	@Autowired
	private InstLectureMapper im;
	
	@Value("${file.lecture.upload-path}") 
	private String uploadImgPath;
	
	/**
	 * 	사용처 : 강사 강의 관리 - 강의리스트
	 * @param instId(강사id), 검색어, 공개여부, 승인여부
	 * @return List<InstLectureDomain>
	 */
	public List<InstLectureDomain> searchInstLectureList(InstLectureSearchDTO sDTO) {
		List<InstLectureDomain> list = null;
		try { 
			list = im.selectInstLectureList(sDTO);
		}catch(PersistenceException pe) {
			pe.printStackTrace();
		}
		
		return list;
	}//method
	
	//강의 추가.
	@Transactional //쿼리문이 모두 성공해야 commit.
	public boolean addLecture(InstLectureDTO ldto) {
		boolean flag = false;
		try {
			int result = im.insertLecture(ldto);
			
			// 2. 저장 성공했고 선택된 스킬이 있다면 스킬 저장 진행
	        if(result == 1 && ldto.getSkills() != null && !ldto.getSkills().isEmpty()) {
	            im.insertLectureSkills(ldto.getLectId(), ldto.getSkills());
	        }
	        flag = result == 1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("강의 등록 중 오류 발생");
		} // end catch
		return flag;
	}// method
	
	//강의 상태 변경
	public boolean modifyAvailability(String lectId, int availability) {
		boolean flag = false;
		try {
			flag = im.updateAvailability(lectId, availability)==1;
	     
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("강의 상태 변경 중 오류 발생");
		} // end catch
		return flag;
	}// method
	
	//강사 강의 상세 조회
	public InstLectureDTO getLectureDetail(String lectId) {
        // 1. 기본 정보 가져오기
        InstLectureDTO ldto = im.selectInstLectureDetail(lectId);
        
        // 2. 스킬 리스트 가져오기
        List<String> skills = im.selectLectureSkills(lectId);
        
        // 3. DTO에 스킬 리스트 세팅 (ldto에 List<String> skills 필드가 있어야 함)
        if(ldto != null) {
            ldto.setSkills(skills);
        }
        
        return ldto;
    }
	


    //  파일 저장 전용 메서드
    public String uploadThumbnail(MultipartFile thumbFile) {
        if (thumbFile == null || thumbFile.isEmpty()) return null;
        
        try {
            String saveName = System.currentTimeMillis() + "_" + thumbFile.getOriginalFilename();
            File saveDir = new File(uploadImgPath);
            if (!saveDir.exists()) saveDir.mkdirs();
            
            thumbFile.transferTo(new File(uploadImgPath, saveName));
            return saveName;
        } catch (IOException e) {
        	System.err.println(">>> 파일 업로드 중 예외 발생!");
            e.printStackTrace();
            return null;
        }
    }
    
    //수정 로직
    @Transactional
    public boolean modifyLecture(InstLectureDTO ldto) {
        // 1. 강의 기본 정보 수정 (성공 시 1)
        int updateResult = im.updateLecture(ldto);
        
        // 기본 정보 수정이 실패(0)했다면 즉시 리턴
        if (updateResult == 0) {
            return false;
        }

        // 2. 기존 스킬 태그 삭제 (항상 수행)
        im.deleteLectureSkills(ldto.getLectId());

        // 3. 새로운 스킬이 있을 때만 insert 수행
        // ldto.getSkills()가 null이 아니고 비어있지 않을 때만 실행하여 에러 방지
        if (ldto.getSkills() != null && !ldto.getSkills().isEmpty()) {
            im.insertLectureSkills(ldto.getLectId(), ldto.getSkills());
        }

        // 기본 정보 수정이 성공했으므로 true 리턴
        return true; 
    }
}//class
