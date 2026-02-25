package kr.co.sist.common.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

@Service
public class FileService {

	/**
	 * 파일을 서버에 저장하고 고유한 파일명을 (이터널)리턴한다
	 * 
	 * @param mf         업로드된 파일 객체
	 * @param uploadPath 저장할 디렉토리 경로, properties에서 의존성 받자. (예: uploadImgPath,
	 *                   uploadDirPath 등)
	 * @return DB에 저장할 고유 파일명 (UUID_파일명)
	 * @throws IOException
	 */
	public String uploadFile(MultipartFile mf, String uploadPath) throws IOException {
		if (mf == null || mf.isEmpty()) {
			return null;
		}

		// 디렉토리가 없으면 생성
		File directory = new File(uploadPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		// 중복 방지를 위한 UUID 생성
		String originalName = mf.getOriginalFilename();
		String saveName = UUID.randomUUID().toString() + "_" + originalName;

		// 파일 저장 경로 설정
		Path targetPath = Paths.get(uploadPath).resolve(saveName);

		// 파일 복사/저장
		Files.copy(mf.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

		return saveName;
	}

	/**
	 * 경로 내의 기존 파일을 삭제한다. (수정 시 이전 파일 제거용)
	 * 
	 * @param fileName
	 * @param uploadPath
	 */
	public void deleteFile(String fileName, String uploadPath) {
		if (fileName != null && !fileName.isEmpty()) {
			File file = new File(uploadPath + File.separator + fileName);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * 물리적 파일 존재 여부 확인
	 * 
	 * @param fileName   DB에 저장된 파일명
	 * @param uploadPath 파일이 저장된 물리적 경로
	 * @return 파일 존재 및 읽기 가능 여부
	 */
	public boolean checkFileExists(String fileName, String uploadPath) {
		if (fileName == null || fileName.isEmpty()) {
			return false;
		} // if
		try {
			// 전체 경로 생성 및 경로 정돈.
			Path filePath = Paths.get(uploadPath).resolve(fileName).normalize();
			//경로로 파일 객체 생성.
			File file = filePath.toFile();
			return file.exists() && file.isFile();
		} catch (Exception e) {
			return false;
		} // catch

	}// method

	/**
	 * 파일 다운로드 - ResponseEntity 생성한다.
	 * ResponseEntity : 브라우저에서 다운로드가 가능한 규격에 맞춘 포장이라고 보면 된다. 
	 * 
	 * @param fileName  다운로드할 파일명
	 * @param uploadDir 파일이 저장된 물리적 경로
	 * @return 다운로드용 ResponseEntity 객체
	 */
	public ResponseEntity<Resource> downloadFile(String fileName, String uploadDir) {
		// 파일명이 없거나 비어있으면 404 에러 리턴.
		if (fileName == null || fileName.isEmpty()) {
			return ResponseEntity.notFound().build();
		} // if

		try {
			// 전체 경로 생성 및 경로 정돈.
			Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
			//파일 경로를 Spring에서 사용 가능한 객체로 생성.
			Resource resource = new UrlResource(filePath.toUri());

			//유효성 검증
			if (!resource.exists()) {
				return ResponseEntity.notFound().build();
			} // if

			// 파일명 인코딩 (한글 깨짐 방지)
			String encodedFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);

			return ResponseEntity.ok() //성공 응답 생성
					//http 헤더에 다운로드용 파일이라고 명시, 다운로드명 지정
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
					//파일 데이터를 http body에 수납.
					.body(resource);

		} catch (Exception e) {
			e.printStackTrace(); //실패 시 에러 로그와 500 err 반환.
			return ResponseEntity.internalServerError().build();
		} // catch

	}// method

}// class