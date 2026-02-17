package kr.co.sist.user.lecture;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.user.lecture.review.UserReviewDTO;
import kr.co.sist.user.lecture.review.UserReviewDomain;
import kr.co.sist.user.lecture.review.UserReviewRangeDTO;

@Service 
public class LectureService {

//	@Autowired(required = false)
	@Autowired
	private LectureMapper lm;
	
	private int pageScaleNum = 8;
	
	//메인화면용 강의리스트, 리뷰정보까지 조회
	public List<LectureDomain> getLectureList(LectureRangeDTO rangeDTO) {
		List<LectureDomain> list = null;
		try { 
			list = lm.selectLectureList(rangeDTO);
		}catch(PersistenceException pe) {
			pe.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		} // end try ~ catch
		return list;
	}//method
	
//------------------페이지네이션 Method---------------------------
	/**
	 * 총 게시물의 수
	 * @param rangeDTO
	 * @return
	 */
	public int totalCnt(LectureRangeDTO rangeDTO) {
		int totalCnt = 0;
		
		try {
			totalCnt = lm.selectLectureCount(rangeDTO);
		} catch (SQLException e) {
			e.printStackTrace();
		} // end try ~ catch
		
		return totalCnt;
	} // totalCnt
	
	/**
	 * 한 화면에 보여줄 페이지의 수
	 * 상단 필드 변수로 기록.
	 * @return
	 */
	public int pageScale() {
		return pageScaleNum;
	} // pageScale
	
	/**
	 * 총 페이지 수
	 * @param totalCount: 전체 게시물의 수
	 * @param pageScale: 한 화면에 보여줄 게시물의 수
	 * @return
	 */
	public int totalPage(int totalCount) {
		return (int)Math.ceil((double)totalCount / pageScale());
	} // totalPage
	
	/**
	 * 페이지의 시작 번호 구하기
	 * @parrm currentPage: 현재 페이지
	 * @parrm pageScale: 한 화면에 보여줄 게시물의 수
	 * @return
	 */
	public int startNum(int currentPage) {
		return currentPage * pageScale() - pageScale() + 1;
	} // startNum
	
	/**
	 * 페이지의 끝 번호 구하기
	 * @parrm startNum: 시작 번호
	 * @parrm pageScale: 한 화면에 보여줄 게시물의 수
	 * @return
	 */
	public int endNum(int startNum) {
		return startNum + pageScale() - 1;
	} // endNum
	
	
	
	/**
	 * 내용이 20자를 초과하면 19자까지 보여주고 뒤에 ...을 붙이는 일
	 * @param list
	 */
	public void contentSubStr(List<UserReviewDomain> reviewList) {
		String content = "";
		for(UserReviewDomain rDomain : reviewList) {
			content = rDomain.getContent();
			if(content != null && content.length() > 19) {
				rDomain.setContent(content.substring(0, 20) + "...");
			} // end if
		} // end for
	} // contentSubStr
	

	//페이지네이션 본격 시작.
	public String pagination2( LectureRangeDTO rangeDTO ) {
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
	
}//class
