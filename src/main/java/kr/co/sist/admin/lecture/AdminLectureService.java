package kr.co.sist.admin.lecture;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.common.util.CryptoUtil;

@Service
public class AdminLectureService {

	@Autowired(required = false)
	private AdminLectureMapper alm;

	@Autowired
	private CryptoUtil cryptoUtil;

	// 강좌 찾기 목록
	public List<String> searchAllCategory() throws PersistenceException {
		return alm.selectAllCategory();
	}

	public int ableLecture(String lectureId) throws PersistenceException {
		return alm.updateOpen(lectureId);
	}

	public int disableLecture(String lectureId) throws PersistenceException {
		return alm.updateStop(lectureId);
	}

	public List<AdminLectureDomain> searchLectureByCategory(AdminLectureSearchDTO alsDTO) throws PersistenceException {
		List<AdminLectureDomain> list = alm.selectLectureByCategory(alsDTO);

		if (list != null) {
			for (AdminLectureDomain domain : list) {
				domain.setInst_name(cryptoUtil.decryptSafe(domain.getInst_name()));
			}
		}

		return list;
	}

	public int countLectureByCategory(AdminLectureSearchDTO alsDTO) throws PersistenceException {
		return alm.selectLectureCount(alsDTO);
	}

	public List<AdminNotApprLectureDomain> searchNotApprLectList(AdminLectureSearchDTO alsDTO)
			throws PersistenceException {
		List<AdminNotApprLectureDomain> list = alm.selectNotApprLectList(alsDTO);

		if (list != null) {
			for (AdminNotApprLectureDomain domain : list) {
				domain.setInst_name(cryptoUtil.decryptSafe(domain.getInst_name()));
			}
		}

		return list != null ? list : Collections.emptyList();
	}

	public int countNotApprLect(AdminLectureSearchDTO alsDTO) throws PersistenceException {
		return alm.selectNotApprCount(alsDTO);
	}

	public List<AdminLectureDetailDomain> searchLectureDetail(String lectureId) throws PersistenceException {
		List<AdminLectureDetailDomain> list = alm.selectLectureDetail(lectureId);

		if (list != null) {
			for (AdminLectureDetailDomain domain : list) {
				domain.setInst_name(cryptoUtil.decryptSafe(domain.getInst_name()));
			}
		}

		return list != null ? list : Collections.emptyList();
	}

	public List<AdminLectureChapterDomain> searchLectureChapter(String lectureId) throws PersistenceException {
		return alm.selectLectureChapter(lectureId);
	}

	public int approvalLecture(String lectureId) throws PersistenceException {
		return alm.updateApproval(lectureId);
	}

	public int rejectReason(String lectureId, String reason) throws PersistenceException {
		return alm.rejcetLecture(lectureId, reason);
	}
}
