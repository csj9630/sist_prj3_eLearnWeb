package kr.co.sist.instructor.lecture.test;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

@Mapper
public interface InstTestMapper {
	//Test테이블에 값 삽입
	public int insertTest(InstTestDTO iDTO) throws PersistenceException;
	//Test_Questtion 테이블에 값 삽입
	public int insertTestQuestion(InstTestDTO iDTO) throws PersistenceException;
	//모든 시험 문제 조회
	public List<InstTestDomain> selectAllInstTest(InstTestViewDTO itvDTO) throws PersistenceException;
	//문제 하나 조회
	public InstTestDomain selectOneInstTest(InstTestViewDTO itvDTO) throws PersistenceException;
	//시험 문제 하나 삭제
	public int deleteOneInstTest(InstTestViewDTO itvDTO) throws PersistenceException;
}
