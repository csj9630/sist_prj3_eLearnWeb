package kr.co.sist.common.question;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

@Mapper
public interface CommonQuestionMapper {
	
	public void insertQuestion(CommonQuestionDTO questionDTO) throws PersistenceException;
	
	public int selectQuestionTotalCnt(CommonQuestionRangeDTO rangeDTO) throws SQLException;
	
	public List<CommonQuestionDomain> selectRangeQuestion(CommonQuestionRangeDTO rangeDTO) throws SQLException;
	
	public CommonQuestionDomain selectQuestionDetail(String qId) throws SQLException;
	
	public int updateQuestion(CommonQuestionDTO questionDTO) throws SQLException;
	
	public int deleteQuestion(CommonQuestionDTO questionDTO) throws SQLException;
	
} // interface
