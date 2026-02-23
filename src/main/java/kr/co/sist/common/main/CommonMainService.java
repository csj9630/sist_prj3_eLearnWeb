package kr.co.sist.common.main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.sist.common.util.CryptoUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonMainService {

    @Autowired
    private CommonMainMapper cmm;
    
    @Autowired
    private CryptoUtil cryptoUtil;

    public List<CommonMainDomain> getCategoryList() {
        List<CommonMainDomain> list = null;
        try {
            list = cmm.selectCategoryList();
        } catch (PersistenceException pe) {
            log.error("카테고리 목록 조회 실패", pe);
        }
        return list;
    }

    public List<CommonMainDomain> getCourseList() {
        List<CommonMainDomain> list = null;
        try {
            list = cmm.selectCourseList();
            if(list != null) {
                for(CommonMainDomain domain : list) {
                    domain.setInstructor(cryptoUtil.decryptSafe(domain.getInstructor()));
                }
            }
        } catch (PersistenceException pe) {
            log.error("강의 목록 조회 실패", pe);
        }
        return list;
    }
    
    public List<CommonMainDomain> getTopCoursesByStudentCount(int limit) {
        List<CommonMainDomain> list = getCourseList();
        if (list == null || list.isEmpty() || limit <= 0) {
            return new ArrayList<>();
        }

        List<CommonMainDomain> copy = new ArrayList<>(list);
        copy.sort(
            Comparator.comparingInt(CommonMainDomain::getUsercount).reversed()
                .thenComparing(Comparator.comparing(CommonMainDomain::getLectId, Comparator.nullsLast(String::compareTo)).reversed())
        );

        int toIndex = Math.min(limit, copy.size());
        return new ArrayList<>(copy.subList(0, toIndex));
    }

}
