package kr.co.sist.admin.announcement;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

@Mapper
public interface AnnouncementMapper {
	
	// 신규 공지사항을 등록
	// <insert id = "insertAnnouncement" parameterType = "announcementDTO">
	public void insertAnnouncement(AnnouncementDTO announcementDTO) throws PersistenceException;
	
	// (만일 키워드가 있다면 키워드를 포함하는 내용의)공지사항 개수를 조회
	// <select id = "selectAnnouncementTotalCnt" parameterType = "rangeDTO" resultType = "int">
	public int selectAnnouncementTotalCnt(RangeDTO rangeDTO) throws SQLException;
	
	// 한 페이지에 출력할(만일 키워드가 있다면 키워드를 포함하는 내용의) 공지사항을 조회
	// <select id = "selectRangeAnnouncement" parameterType = "rangeDTO" resultType = "announcementDomain">
	public List<AnnouncementDomain> selectRangeAnnouncement(RangeDTO rangeDTO) throws SQLException;
	
	// 공지사항 아이디를 받아서 해당하는 공지사항 하나를 자세히 조회
	// <select id = "selectAnnouncementDetail" parameterType = "Integer" resultType = "announcementDomain">
	public AnnouncementDomain selectAnnouncementDetail(int annId) throws SQLException;
	
	// 공지사항을 읽었을 때 views(조회 수) 증가
	// <update id = "updateAnnouncementView" parameterType = "Integer">
	public void updateAnnouncementView(int annId) throws SQLException;
	
	// 공지사항 아이디와 관리자 아이디를 입력 받아 관리자임을 확인 후 해당 공지사항의 내용과 아이피를 변경
	// <update id = "updateAnnouncement" parameterType = "announcementDTO">
	public int updateAnnouncement(AnnouncementDTO announcementDTO) throws SQLException;
	
	// 공지사항 아이디와 관리자 아이디를 입력 받아 관리자임을 확인 후 해당 공지사항을 삭제
	// <delete id = "deleteAnnouncement" parameterType = "announcementDTO">
	public int deleteAnnouncement(AnnouncementDTO announcementDTO) throws SQLException;
	
} // interface
