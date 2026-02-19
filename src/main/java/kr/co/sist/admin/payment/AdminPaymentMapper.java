package kr.co.sist.admin.payment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminPaymentMapper {
	//강사명
	public List<String> selectAllInst();
	//강의 총 수익
	public int selectAllProfit();
	//관리자 수익
	public int selectAdminProfit();
	public List<LectProfitDomain> selectLectProfit(AdminPaymentSearchDTO apsDTO);
}
