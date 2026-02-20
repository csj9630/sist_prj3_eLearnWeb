package kr.co.sist.common.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    /**
     * 파일을 서버에 저장하고 고유한 파일명을 반환합니다.
     * @param mf 업로드된 파일 객체
     * @param uploadPath 저장할 디렉토리 경로 (예: uploadImgPath, uploadDirPath 등)
     * @return DB에 저장할 고유 파일명 (UUID_파일명)
     * @throws IOException
     */
    public String uploadFile(MultipartFile mf,String uploadPath) throws IOException {
        if (mf == null || mf.isEmpty()) {
            return null;
        }

        // 1. 디렉토리가 없으면 생성 (팀원 코드 참고)
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 2. 중복 방지를 위한 UUID 생성 (수업 코드 참고)
        String originalName = mf.getOriginalFilename();
        String saveName = UUID.randomUUID().toString() + "_" + originalName;

        // 3. 파일 저장 경로 설정
        Path targetPath = Paths.get(uploadPath).resolve(saveName);

        // 4. 파일 복사/저장 (Files.copy 방식 사용 - 더 안전함)
        Files.copy(mf.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return saveName;
    }

    /**
     * 기존 파일을 삭제합니다 (수정 시 이전 파일 제거용)
     */
    public void deleteFile(String fileName, String uploadPath) {
        if (fileName != null && !fileName.isEmpty()) {
            File file = new File(uploadPath + File.separator + fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}