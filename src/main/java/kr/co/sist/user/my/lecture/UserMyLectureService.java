package kr.co.sist.user.my.lecture;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMyLectureService {

    @Autowired
    private UserMyLectureMapper userMyLectureMapper;

    // 내 강의 목록 조회
    public List<UserMyLectureDomain> searchMyLectureList(String userId) {
        return userMyLectureMapper.selectMyLectureList(userId);
    }
}