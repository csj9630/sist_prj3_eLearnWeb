package kr.co.sist.instructor.payment;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("instructorPaymentService")
public class PaymentService {

    @Autowired
    private InstPaymentMapper instPaymentMapper;
    
    // 나의 총 매출액
    public int getMyPaySum(String instId) {
        return instPaymentMapper.selectMyPaySum(instId);
    }
    
    // 강의별 매출 리스트
    public List<PaymentSumDTO> getMyPaySumByLect(String instId) {
        return instPaymentMapper.selectMyPaySumByLect(instId);
    }
    
    // 총 수강생 수
    public int getMyUserCount(String instId) {
        return instPaymentMapper.selectMyUserCount(instId);
    }
}