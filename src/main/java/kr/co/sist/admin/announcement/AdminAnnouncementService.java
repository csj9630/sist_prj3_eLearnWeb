package kr.co.sist.admin.announcement;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAnnouncementService {
	
	@Autowired
	private AdminAnnouncementMapper am;
	
	/**
	 * 총 게시물의 수
	 * @param rangeDTO
	 * @return
	 */
	public int totalCnt(AdminAnnouncementRangeDTO rangeDTO) {
		int totalCnt = 0;
		
		try {
			totalCnt = am.selectAnnouncementTotalCnt(rangeDTO);
		} catch (SQLException e) {
			e.printStackTrace();
		} // end try ~ catch
		
		return totalCnt;
	} // totalCnt
	
	/**
	 * 한 화면에 보여줄 페이지의 수
	 * @return
	 */
	public int pageScale() {
		return 10;
	} // pageScale
	
	/**
	 * 총 페이지 수
	 * @param totalCount: 전체 게시물의 수
	 * @param pageScale: 한 화면에 보여줄 게시물의 수
	 * @return
	 */
	public int totalPage(int totalCount, int pageScale) {
		return (int)Math.ceil((double)totalCount / pageScale);
	} // totalPage
	
	/**
	 * 페이지의 시작 번호 구하기
	 * @param currentPage: 현재 페이지
	 * @param pageScale: 한 화면에 보여줄 게시물의 수
	 * @return
	 */
	public int startNum(int currentPage, int pageScale) {
		return currentPage * pageScale - pageScale + 1;
	} // startNum
	
	/**
	 * 페이지의 끝 번호 구하기
	 * @param startNum: 시작 번호
	 * @param pageScale: 한 화면에 보여줄 게시물의 수
	 * @return
	 */
	public int endNum(int startNum, int pageScale) {
		return startNum + pageScale - 1;
	} // endNum
	
	public boolean addAnnouncement(AdminAnnouncementDTO aDTO) {
		
		boolean flag = false;
		
		try {
			am.insertAnnouncement(aDTO);
			flag = true;
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} // end try ~ catch
		
		return flag;
		
	} // addAnnouncement
	
	/**
	 * 시작 번호, 끝 번호, 검색 필드, 검색 키워드를 사용한 게시글 검색
	 * @param rangeDTO
	 * @return
	 */
	public List<AdminAnnouncementDomain> searchAnnouncementList(AdminAnnouncementRangeDTO rangeDTO) {
		List<AdminAnnouncementDomain> list = null;
		
		try {
			list = am.selectRangeAnnouncement(rangeDTO);
		} catch (SQLException e) {
			e.printStackTrace();
		} // end try ~ catch
		
		return list;
	} // searchAnnouncementList
	
	/**
	 * 제목이 20자를 초과하면 19자까지 보여주고 뒤에 ...을 붙이는 일
	 * @param list
	 */
	public void titleSubStr(List<AdminAnnouncementDomain> announcementList) {
		String title = "";
		for(AdminAnnouncementDomain aDTO : announcementList) {
			title = aDTO.getTitle();
			if(title != null && title.length() > 19) {
				aDTO.setTitle(title.substring(0, 20) + "...");
			} // end if
		} // end for
	} // titleSubStr
	
	/**
	 * 페이지네이션 [<<] ... [1][2][3] ... [>>]
	 * @param rangeDTO
	 * @return
	 */
	public String pagination(AdminAnnouncementRangeDTO rangeDTO) {
		StringBuilder pagination = new StringBuilder();
		
		// 1. 한 화면에 보여줄 pagination의 수
		int pageNumber = 3;
		
		// 2. 화면에 보여줄 시작 페이지 번호
		// 1, 2, 3 => 1로 시작
		// 4, 5, 6 => 4로 시작
		// 7, 8, 9 => 7로 시작
		// 10, 11, 12 => 10으로 시작
		// ...
		int startPage = ((rangeDTO.getCurrentPage() - 1)/pageNumber) * pageNumber + 1;
		
		// 3. 화면에 보여줄 마지막 페이지 번호
		int endPage = (((startPage - 1) + pageNumber) / pageNumber) * pageNumber;
		
		// 4. 총 페이지 수가 연산된 마지막 페이지 수보다 작다면 총 페이지 수가 마지막 페이지 수로 설정
		if(rangeDTO.getTotalPage() <= endPage) {
			endPage = rangeDTO.getTotalPage();
		} // end if
		
		// 5. 첫 페이지가 인덱스 화면(1 페이지)이 아닌 경우
		int movePage = 0;
		StringBuilder prevMark = new StringBuilder();
		prevMark.append("[ &lt;&lt; ]");
		/* prevMark.append("<li class='page-item'><a class='page-link' href='#'>Previous</a></li>"); */
		if(rangeDTO.getCurrentPage() > pageNumber) { // 시작 페이지 번호가 3보다 크면 
			movePage = startPage - 1; // 4, 5, 6 -> 3 -> 1 / 7, 8, 9 -> 6 -> 4 / ...
			prevMark.delete(0, prevMark.length()); // 이전에 링크가 없는 [<<] 삭제
			prevMark
			.append("[ <a href = '")
			.append(rangeDTO.getUrl())
			.append("?currentPage=")
			.append(movePage);
			
			// 검색 키워드가 있다면 검색 키워드를 붙인다.
			if(rangeDTO.getKeyword() != null && !rangeDTO.getKeyword().isEmpty()) {
				prevMark
				.append("&field=")
				.append(rangeDTO.getField())
				.append("&keyword=")
				.append(rangeDTO.getKeyword());
			} // end if
			
			prevMark.append("' class = 'prevMark'>&lt;&lt;</a> ]");
			
		} // end if
		
		// 6. 시작 페이지 번호부터 끝 페이지 번호까지 화면에 출력
		StringBuilder pageLink = new StringBuilder();
		movePage = startPage;
		
		while(movePage <= endPage) {
			
			if(movePage == rangeDTO.getCurrentPage()) { // 현재 페이지: 링크 X
				pageLink
				.append("[ <span class = 'currentPage'>")
				.append(movePage).append("</span> ]");
				
			} else { // 현재 페이지가 아닌 다른 페이지: 링크 O
				pageLink
				.append("[ <a class = 'notCurrentPage' href = '")
				.append(rangeDTO.getUrl())
				.append("?currentPage=")
				.append(movePage);
				
				// 검색 키워드가 있다면 검색 키워드를 붙인다.
				if(rangeDTO.getKeyword() != null && !rangeDTO.getKeyword().isEmpty()) {
					pageLink
					.append("&field=")
					.append(rangeDTO.getField())
					.append("&keyword=")
					.append(rangeDTO.getKeyword());
					
				} // end if
				
				pageLink
				.append("'>")
				.append(movePage)
				.append("</a> ]");
				
			} // end if ~ else
			
			movePage++;
			
		} // end while
		
		// 7. 뒤에 페이지가 더 있는 경우
		StringBuilder nextMark = new StringBuilder("[ &gt;&gt; ]");
		if(rangeDTO.getTotalPage() > endPage) { // 뒤에 페이지가 더 있음.
			
			movePage = endPage + 1;
			
			nextMark.delete(0, nextMark.length());
			nextMark
			.append("[ <a class = 'nextMark' href = '")
			.append(rangeDTO.getUrl())
			.append("?currentPage=")
			.append(movePage);
			
			// 검색 키워드가 있다면 검색 키워드를 붙인다.
			if(rangeDTO.getKeyword() != null && !rangeDTO.getKeyword().isEmpty()) {
				nextMark
				.append("&field=")
				.append(rangeDTO.getField())
				.append("&keyword=")
				.append(rangeDTO.getKeyword());
				
			} // end if
			
			nextMark
			.append("'>&gt;&gt;</a> ]");
			
		} // end if
		
		pagination
		.append(prevMark)
		.append("...")
		.append(pageLink)
		.append("...")
		.append(nextMark); 
		
		return pagination.toString();
		
	} // pagination
	
	public String pagination2( AdminAnnouncementRangeDTO rangeDTO ) {
		StringBuilder pagination=new StringBuilder();
		//1. 한 화면에 보여줄 pagination의 수.
		int pageNumber=3;
		//2. 화면에 보여줄 시작페이지 번호. 1,2,3 => 1로 시작 , 4,5,6=> 4, 7,8,9=>7
		int startPage= ((rangeDTO.getCurrentPage()-1)/pageNumber)*pageNumber+1;
		//3. 화면에 보여줄 마지막 페이지 번호 1,2,3 =>3
		int endPage= (((startPage-1)+pageNumber)/pageNumber)*pageNumber;
		//4. 총페이지수가 연산된 마지막 페이지수보다 작다면 총페이지 수가 마지막 페이지수로 설정
		//456 인경우 > 4로 설정
		if( rangeDTO.getTotalPage() <= endPage) {
			endPage=rangeDTO.getTotalPage();
		}//end if
		//5.첫페이지가 인덱스 화면 (1페이지) 가 아닌 경우
		int movePage=0;
		StringBuilder prevMark=new StringBuilder();
		prevMark.append("<li class='page-item disabled'>");
		prevMark.append("<a class='page-link'>Previous</a>");
		prevMark.append("</li>");
		//prevMark.append("<li class='page-item'><a class='page-link' href='#'>Previous</a></li>");
		if(rangeDTO.getCurrentPage() > pageNumber) {// 시작페이지 번호가 3보다 크면 
			movePage=startPage-1;// 4,5,6->3 ->1 , 7 ,8 ,9 -> 6 -> 4
			prevMark.delete(0, prevMark.length());// 이전에 링크가 없는 [<<] 삭제
			prevMark.append("<li class='page-item'><a class='page-link' href='").append(rangeDTO.getUrl())
			.append("?currentPage=").append(movePage);
			//검색 키워드가 있다면 검색 키워드를 붙인다.
			if( rangeDTO.getKeyword() != null && !rangeDTO.getKeyword().isEmpty() ) {
				prevMark.append("&field=").append(rangeDTO.getField())
				.append("&keyword=").append(rangeDTO.getKeyword());
			}//end if
			prevMark.append("'>Previous</a></li>");
		}//end if
		
		//6.시작페이지 번호부터 끝 번호까지 화면에 출력
		StringBuilder pageLink=new StringBuilder();
		movePage=startPage;
		
		while( movePage <= endPage ) {
			if( movePage == rangeDTO.getCurrentPage()) { //현재 페이지 : 링크 x
				pageLink.append("<li class='page-item active page-link'>") 
				.append(movePage).append("</li>");
			}else {//현재페이지가 아닌 다른 페이지 : 링크 O
				pageLink.append("<li class='page-item'><a class='page-link' href='")
				.append(rangeDTO.getUrl()).append("?currentPage=").append(movePage);
				//검색 키워드가 있다면 검색 키워드를 붙인다.
				if( rangeDTO.getKeyword() != null && !rangeDTO.getKeyword().isEmpty() ) {
					pageLink.append("&field=").append(rangeDTO.getField())
					.append("&keyword=").append(rangeDTO.getKeyword());
				}//end if
				pageLink.append("'>").append(movePage).append("</a>");
				
			}//else
			
			movePage++;
		}//end while
		
		//7. 뒤에 페이지가 더 있는 경우
		StringBuilder nextMark=new StringBuilder("<li class='page-item page-link'>Next</li>");
		if( rangeDTO.getTotalPage() > endPage) { // 뒤에 페이지가 더 있음.
			movePage= endPage+1;
			nextMark.delete(0, nextMark.length());
			nextMark.append("<li class='page-item page-link'><a href='")
			.append(rangeDTO.getUrl()).append("?currentPage=").append(movePage);
			if( rangeDTO.getKeyword() != null && !rangeDTO.getKeyword().isEmpty() ) {
				nextMark.append("&field=").append(rangeDTO.getField())
				.append("&keyword=").append(rangeDTO.getKeyword());
			}//end if
			
			nextMark.append("'>Next</a></li>");
			
		}//end if
		pagination.append("<nav aria-label='...'>")
		.append("  <ul class='pagination'>");
		pagination.append(prevMark).append(pageLink).append(nextMark);
		pagination.append("</ul>")
		.append("  </nav>");
		
		return pagination.toString();
	}//pagination
	
	/**
	 * 상세보기
	 * @param num
	 * @return
	 */
	public AdminAnnouncementDomain searchOneAnnouncement(int annId) {
		AdminAnnouncementDomain aDomain = null;
		
		try {
			aDomain = am.selectAnnouncementDetail(annId);
		} catch (SQLException e) {
			e.printStackTrace();
		} // end try ~ catch
		System.out.println("공지사항 도메인 서비스: " + aDomain);
		
		return aDomain;
		
	} // searchOneAnnouncement
	
	public void modifyAnnouncementCnt(int annId) {
		
		try {
			am.updateAnnouncementView(annId);
		} catch (SQLException e) {
			e.printStackTrace();
		} // end try ~ catch
		
	} // modifyAnnouncementCnt
	
	public boolean modifyAnnouncement(AdminAnnouncementDTO aDTO) {
		boolean flag = false;
		
		try {
			flag = am.updateAnnouncement(aDTO) == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public boolean removeAnnouncement(AdminAnnouncementDTO aDTO) {
		boolean flag = false;
		
		try {
			flag = am.deleteAnnouncement(aDTO) == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
} // class
