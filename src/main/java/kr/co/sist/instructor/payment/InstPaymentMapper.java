package kr.co.sist.instructor.payment;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InstPaymentMapper {

    // 나의 총 매출액
    int selectMyPaySum(String instId);

    // 강의별 매출 리스트 조회
    List<PaymentSumDTO> selectMyPaySumByLect(String instId);

    // 총 수강생 수 조회
    int selectMyUserCount(String instId);
}