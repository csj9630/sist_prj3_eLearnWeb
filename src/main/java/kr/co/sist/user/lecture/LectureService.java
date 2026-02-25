package kr.co.sist.user.lecture;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.common.util.CryptoUtil;
import kr.co.sist.common.lecture.CommonLectureService;
import kr.co.sist.common.lecture.SkillDomain;
import kr.co.sist.user.lecture.board.LectureDomain;
import kr.co.sist.user.lecture.board.LectureRangeDTO;
import kr.co.sist.user.lecture.chapter.ChapterService;
import kr.co.sist.user.lecture.detail.LectureAllDetailDomain;
import kr.co.sist.user.lecture.review.UserReviewDomain;
import lombok.Setter;

@Service 
public class LectureService {

//	@Autowired(required = false)
	@Autowired
	private LectureMapper lm;
	
	@Autowired
	private ChapterService cs;
	
	@Autowired
	private CryptoUtil cryptoUtil;
	
	@Autowired
	private CommonLectureService commonService; // 공통 서비스 주입
	
	@Setter
	private int pageScale;
	
	
	
	
	//메인화면용 강의리스트, 리뷰정보까지 조회
	public List<LectureDomain> getLectureList(LectureRangeDTO rangeDTO) {
		List<LectureDomain> list = null;
		
		//페이지 번호 계산 (BETWEEN문)
	    int startNum = startNum(rangeDTO.getCurrentPage());
	    rangeDTO.setStartNum(startNum);
	    rangeDTO.setEndNum(endNum(startNum));
		
	    //검색 조건
	    if("".equals(rangeDTO.getCatId())) { 
	    	rangeDTO.setCatId(null); 
	    }
	    if("".equals(rangeDTO.getSkillName())) {
	    	rangeDTO.setSkillName(null); 
	    }
	    
	    
		try { 
			list = lm.selectLectureList(rangeDTO);
			if(list != null) {
				for(LectureDomain domain : list) {
					domain.setInstName(cryptoUtil.decryptSafe(domain.getInstName()));
				}//end for
			}//end if
		}catch(PersistenceException pe) {
			pe.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		} // end try ~ catch
		return list;
	}//method
	
	//강의 상세 페이지 조회
	public LectureAllDetailDomain getLectureAllDetail(String lectId) {
		LectureAllDetailDomain alldto = new LectureAllDetailDomain();
		
	    try {
	    	alldto.setDetail(lm.selectLectureDetail(lectId)); //강의 상세 정보
	    	alldto.setChapter(cs.searchChapterList(lectId)); // 챕터 커리큘럼 정보 기존 서비스 재사용
	    	alldto.setReview(lm.selectLectureReviews(lectId)); // 리뷰 정보
		} catch (PersistenceException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end catch
	    
	    return alldto;
	}//method
	
	//검색 필드 한번에 묶을 용도.
	public Map<String, Object> getFilters() {
	    Map<String, Object> map = new HashMap<>();
	    // lectureMapper 하나로 다 처리
	    try {
	    	map.put("categoryList", commonService.getAllCategories());
			map.put("skillList", commonService.getAllSkills());
		} catch (PersistenceException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return map;
	}//method
	
	//해당 강의의 스킬 리스트 조회
	public List<String> getSkillList(String lectId){
		List<String> list = new ArrayList<String>();
		for (SkillDomain sd : commonService.getAllSkills( lectId)) {
			list.add(sd.getName());
		}
		return list;
	}
	
//-------------------------------------페이지네이션 Method---------------------------------------------
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
		return pageScale;
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
		
		StringBuilder prevMark=new StringBuilder();
		StringBuilder pageLink=new StringBuilder();
		StringBuilder nextMark=new StringBuilder();
		StringBuilder firstMark = new StringBuilder(); // 맨앞
		StringBuilder lastMark = new StringBuilder();  // 맨끝
		
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
		
		
		// 🔵 맨 앞 버튼
		firstMark.append("<li class='page-item'><a class='page-link' href='")
        .append(makeUrl(rangeDTO, 1))
        .append("'>&laquo;</a></li>"); // « 기호
		
		// 4. [맨끝] 버튼: totalPage로
		lastMark.append("<li class='page-item'><a class='page-link' href='")
	            .append(makeUrl(rangeDTO, rangeDTO.getTotalPage()))
	            .append("'>&raquo;</a></li>"); // » 기호
		
		
		//5.첫페이지가 인덱스 화면 (1페이지) 가 아닌 경우
		int movePage=0;
		
		
		prevMark.append("<li class='page-item disabled'>");
		prevMark.append("<a class='page-link'>PREV</a>");
		prevMark.append("</li>");
		
		//◀️ 이전 버튼
		if(rangeDTO.getCurrentPage() > pageNumber) {// 시작페이지 번호가 3보다 크면 
			movePage=startPage-1;// 4,5,6->3 ->1 , 7 ,8 ,9 -> 6 -> 4
			prevMark.delete(0, prevMark.length());// 이전에 링크가 없는 [<<] 삭제
			prevMark
				.append("<li class='page-item'><a class='page-link' href='")
				.append(makeUrl(rangeDTO, movePage))
				.append("'>Previous</a></li>");
		}//end if
		
		//6.페이지 번호 [1][2][3]...
		movePage=startPage;
		
		while( movePage <= endPage ) {
			if( movePage == rangeDTO.getCurrentPage()) { //현재 페이지 : 링크 x
				pageLink
					.append("<li class='page-item active page-link'>") 
					.append(movePage)
					.append("</li>");
			}else {//현재페이지가 아닌 다른 페이지 : 링크 O
				pageLink.append("<li class='page-item'><a class='page-link' href='")
				.append(makeUrl(rangeDTO, movePage))
				.append("'>")
				.append(movePage)
				.append("</a>")
				.append("</li>");
				
			}//else
			
			movePage++;
		}//end while
		
		//7. ▶️ 뒤에 페이지가 더 있는 경우(다음버튼)
		nextMark.append("<li class='page-item page-link'>NEXT</li>");
		
		if( rangeDTO.getTotalPage() > endPage) { // 뒤에 페이지가 더 있음.
			movePage= endPage+1;
			nextMark.delete(0, nextMark.length());
			nextMark.append("<li class='page-item page-link'><a href='")
			.append(makeUrl(rangeDTO, movePage))
			.append("'>Next</a></li>");
			
		}//end if
		
		//합체
		pagination
			.append("<nav aria-label='...'>")
			.append("  <ul class='pagination'>");
		pagination
			.append(firstMark)
			.append(prevMark)
			.append(pageLink)
			.append(nextMark)
			.append(lastMark);
		pagination
			.append("</ul>")
			.append("  </nav>");
		
		return pagination.toString();
	}//pagination
	
	// 검색 조건을 유지하며 URL을 만들어주는 헬퍼 메서드
	private String makeUrl(LectureRangeDTO rDTO, int page) {
	    StringBuilder url = new StringBuilder();
	    url.append(rDTO.getUrl()).append("?currentPage=").append(page);
	    
	    if (rDTO.getCatId() != null && !rDTO.getCatId().isEmpty()) {
	        url.append("&catId=").append(rDTO.getCatId());
	    }
	    if (rDTO.getSkillName() != null && !rDTO.getSkillName().isEmpty()) {
	        url.append("&SkillName=").append(rDTO.getSkillName());
	    }

	    if (rDTO.getKeyword() != null && !rDTO.getKeyword().isEmpty()) {
	        url.append("&field=").append(rDTO.getField()) // field 정보가 반드시 같이 가야 함
	           .append("&keyword=").append(rDTO.getKeyword());
	    }
	    return url.toString();
	}//method
//-------------------------------------페이지네이션 Method---------------------------------------------
	

	
}//class
