package kr.co.sist.user.lecture.test;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

@Mapper
public interface StuTestMapper {
	// 시험 문제 조회 후 사용자 화면에 뿌려주기
	public List<StuTestDomain> selectUserTest(String lectId) throws PersistenceException;
}
