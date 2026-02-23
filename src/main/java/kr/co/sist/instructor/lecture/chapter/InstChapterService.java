package kr.co.sist.instructor.lecture.chapter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sist.common.file.FileService;

@Service
public class InstChapterService {

	@Autowired
	private InstChapterMapper icMapper;

	@Autowired
    private FileService fileService;
	
	@Value("${user.upload-doc-dir}") // 강의 자료 경로
	private String uploadDocPath;

	/**
	 * 특정 강의의 전체 챕터 목록을 조회 (NUM 순)
	 * 
	 * @param lectId 강의 ID
	 * @return 챕터 목록
	 */
	public List<InstChapterDTO> searchChapterList(String lectId) {
		return icMapper.selectChapterList(lectId);
	}

	/**
	 * 특정 챕터의 상세 정보를 조회 (수정 폼 바인딩용)
	 * 
	 * @param chptrId 챕터 ID
	 * @return 챕터 상세 데이터
	 */
	public InstChapterDTO searchChapterDetail(String chptrId) {
		return icMapper.selectChapterDetail(chptrId);
	}

	/**
	 * 새로운 챕터를 등록
	 * 
	 * @param icDTO icDTO 챕터 정보 (num, name, video, length 등 포함)
	 * @param mf    강의자료 업로드
	 * @return 성공 여부
	 * @throws IOException
	 */
	@Transactional
	public boolean addChapter(InstChapterDTO icDTO, MultipartFile mf) throws IOException {
		int result = 0;

		// 1. 파일이 있으면 업로드 처리
		if (mf != null && !mf.isEmpty()) {
			String savedName = fileService.uploadFile(mf, uploadDocPath);
			icDTO.setDoc(savedName); // DTO의 doc 컬럼에 파일명 세팅
		}
		result = icMapper.insertChapter(icDTO);

		return result > 0;
	}

	/**
	 * 기존 챕터 정보를 수정
	 * 
	 * @param icDTO 수정할 챕터 정보
	 * @param mf    새로 업로드할 강의자료
	 * @return 성공 여부
	 */
	@Transactional
	public boolean modifyChapter(InstChapterDTO icDTO, MultipartFile mf) throws IOException {
		// 1. 새로운 파일이 업로드 된 경우
		if (mf != null && !mf.isEmpty()) {
			// (선택사항) 기존 파일이 있다면 삭제하는 로직 추가 가능
			InstChapterDTO oldData = icMapper.selectChapterDetail(icDTO.getChptrId());
			fileService.deleteFile(oldData.getDoc(), uploadDocPath);

			String savedName = fileService.uploadFile(mf, uploadDocPath);
			icDTO.setDoc(savedName);
		}
		// 파일(doc)이 null이 아닐 때만 업데이트하는 로직은 Mapper의 <set>에서 처리됨
		int result = icMapper.updateChapter(icDTO);
		return result > 0;
	}

	/**
	 * 챕터 삭제
	 * 
	 * @param chptrId 챕터 ID
	 * @return 성공 여부
	 */
	@Transactional
	public boolean removeChapter(String chptrId) {
		int result = icMapper.deleteChapter(chptrId);
		return result > 0;
	}

}// class
