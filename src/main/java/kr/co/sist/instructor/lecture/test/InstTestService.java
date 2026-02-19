package kr.co.sist.instructor.lecture.test;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstTestService {


	@Autowired(required = false)
	private InstTestMapper iMapper;

	// 시험문제 페이지 조회 ( 모든 시험 문제 조회 )
	public List<InstTestDomain> searchTest(InstTestViewDTO itvDTO) {
		List<InstTestDomain> list = null;
		try {
			list = iMapper.selectAllInstTest(itvDTO);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} // end catch

		return list;
	}// searchTest

	// 시험문제 - 하나 조회 ( ajax로 구현 )
	public String searchOneTest(InstTestViewDTO itvDTO) {
		InstTestDomain itDomain = null;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("flag", false);
		try {
			itDomain = iMapper.selectOneInstTest(itvDTO);

			if (itDomain != null) {
				jsonObj.put("flag", true);
				
				JSONObject jsonData = new JSONObject();
				jsonData = new JSONObject();
				jsonData.put("id", itDomain.getId());
				jsonData.put("lectId", itDomain.getLectId());
				jsonData.put("qid", itDomain.getQid());
				jsonData.put("content", itDomain.getContent());
				jsonData.put("opt1", itDomain.getOpt1());
				jsonData.put("opt2", itDomain.getOpt2());
				jsonData.put("opt3", itDomain.getOpt3());
				jsonData.put("opt4", itDomain.getOpt4());
				jsonData.put("ans", itDomain.getAns());
				jsonData.put("exp", itDomain.getExp());
				jsonData.put("num", itDomain.getNum());

				jsonObj.put("data", jsonData);
			}
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} // end catch
		return jsonObj.toJSONString();
	}// searchOneTest


	// 시험문제 - 삭제 (ajax 구현)
	public String removeTest(InstTestViewDTO itvDTO) {
		boolean removeResult = false;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("flag", false);
		try {
			//삭제 성공 시 true 반환
			if(iMapper.deleteOneInstTest(itvDTO) == 1) {
				removeResult = true;
			}

				jsonObj.put("removeResult", removeResult);
				jsonObj.put("flag", true);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} // end catch
		return jsonObj.toJSONString();
	}// removeTest

	// 시험문제 - 수정
	public String modifyTest(InstTestDTO iDTO) {
		boolean updateResult = false;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("flag", false);
		try {
			//수정 성공 시 true 반환
			if(iMapper.updateInstTest(iDTO) == 1) {
				updateResult = true;
			}

				jsonObj.put("updateResult", updateResult);
				jsonObj.put("flag", true);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} // end catch
		return jsonObj.toJSONString();
	}// modifyTest

	// 시험문제 - 생성
	public String writeTest(InstTestDTO iDTO) {
		boolean Insertflag = false;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("flag", false);
		try {
			//존재하는 시험인지 확인
			String flagtestId = iMapper.selectTestIdByLectId(iDTO.getLectId());
			
			if (flagtestId == null) {
	            // 시험이 없다면 새로운 시험지(test테이블에) 생성.(T1,T2...)
	            iMapper.insertTest(iDTO); 
	        } else {
	            // test에 id값이 이미 존재한다면 ID를 생성하지 말고 재사용.
	            iDTO.setId(flagtestId); 
	        }
			
			if(iMapper.insertTestQuestion(iDTO) == 1) {
				Insertflag = true;
			}
			jsonObj.put("updateResult", Insertflag);
			jsonObj.put("flag", true);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}
		return jsonObj.toJSONString();
	}// writeTest

}// class
