package kr.co.sist.user.my.lecture;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.sist.common.util.CryptoUtil;

@Service
public class UserMyLectureService {

    @Autowired
    private UserMyLectureMapper userMyLectureMapper;
    
    @Autowired
    private CryptoUtil cryptoUtil;

    // 내 강의 목록 조회
    public List<UserMyLectureDomain> searchMyLectureList(String userId, String title) {
        List<UserMyLectureDomain> list = userMyLectureMapper.selectMyLectureList(userId, title);
        if(list != null) {
            for(UserMyLectureDomain domain : list) {
                domain.setInstName(cryptoUtil.decryptSafe(domain.getInstName()));
            }//end for
        }//end if
        return list;
    }//searchMyLectureList

}//class
