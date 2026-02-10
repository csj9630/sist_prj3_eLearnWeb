package kr.co.sist.admin.payment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminPaymentMapper {
	
	public List<LectProfitDomain> selectLectProfit();
}
