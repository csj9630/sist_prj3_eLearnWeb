package kr.co.sist.instructor.payment;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InstPaymentMapper {

    // 월별 총 매출액 조회
	int selectMonthPaySum(@Param("instId") String instId, @Param("searchMonth") String searchMonth);
    
    // 누적 총 수입 조회
    int selectTotalPaySum(String instId);

    // 강의별 매출 리스트 조회
    List<PaymentSumDTO> selectMyPaySumByLect(String instId);

}//interface