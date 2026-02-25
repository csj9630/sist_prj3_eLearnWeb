package kr.co.sist.instructor.payment;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("instructorPaymentService")
public class PaymentService {

    @Autowired
    private InstPaymentMapper instPaymentMapper;
    
    // 강의별 매출 리스트
    public List<PaymentSumDTO> getMyPaySumByLect(String instId) {
        return instPaymentMapper.selectMyPaySumByLect(instId);
    }//getMyPaySumByLect
    
    
    //누적 총 수입
    public int getTotalPaySum(String instId) {
        return instPaymentMapper.selectTotalPaySum(instId);
    }//getTotalPaySum
    
    
    //월별 수익 조회
    public int getMonthPaySum(String instId, String searchMonth) {
        // 날짜가 없으면 현재 날짜로 설정
        if(searchMonth == null || searchMonth.equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
            searchMonth = sdf.format(new java.util.Date());
        }//end if
        
        return instPaymentMapper.selectMonthPaySum(instId, searchMonth);
    }//getMonthPaySum

    
}//class
