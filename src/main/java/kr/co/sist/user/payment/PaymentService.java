package kr.co.sist.user.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userPaymentService")
public class PaymentService {

    @Autowired
    private UserPaymentMapper userPaymentMapper;

    public boolean addLectureToCart(String userId, String lectId) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("lectId", lectId);

        if (userPaymentMapper.checkCartDuplicate(params) == 0) {
            MyCartDTO mcDTO = new MyCartDTO();
            mcDTO.setUserId(userId);
            mcDTO.setLectId(lectId);
            return userPaymentMapper.insertCart(mcDTO) > 0;
        }
        return false;
    }

    public List<MyCartDTO> getMyCart(String userId) {
        return userPaymentMapper.selectCartByUserId(userId);
    }

    public boolean deleteCartLectures(String userId, List<MyCartDTO> mcDTO) {
        int deleteCount = 0;
        for(MyCartDTO dto : mcDTO) {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("lectId", dto.getLectId());
            deleteCount += userPaymentMapper.deleteCartList(params);
        }
        return deleteCount > 0;
    }

    // [핵심 수정] 결제 로직 재정비
    @Transactional(rollbackFor = Exception.class)
    public boolean purchaseLectures(String userId, List<MyCartDTO> mcDTO, int totalPrice) throws Exception {
        
        // 1. 공통 ID 생성 (Java에서 생성)
        String payRecId = "PD" + System.currentTimeMillis(); 

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("payRecId", payRecId); // 생성한 ID 사용
        params.put("totalPrice", totalPrice);

        // 2. PAYMENT_DETAIL 생성 (selectKey 제거된 XML 쿼리 사용)
        int resultRec = userPaymentMapper.insertPaymentRecord(params);

        if(resultRec > 0) {
            // 3. PAYMENT (영수증) 생성 - [주석 해제 필수!]
            userPaymentMapper.insertPayment(params);

            // 4. 각 강의별 처리
            for(MyCartDTO cartItem : mcDTO) {
                params.put("lectId", cartItem.getLectId());

                // 이미 수강 중인지 확인
                if(userPaymentMapper.checkMyLectureDuplicate(params) > 0) {
                    userPaymentMapper.deleteCartList(params);
                    continue; 
                }
                
                // 4-1. 상세 아이템 매핑
                userPaymentMapper.insertPaymentItem(params);

                // 4-2. 내 강의실 등록
                userPaymentMapper.insertMyLecture(params);

                // 4-3. 수강생 수 증가
                userPaymentMapper.updateLectureUserCount(cartItem.getLectId());

                // 4-4. 장바구니 삭제
                userPaymentMapper.deleteCartList(params);
            }
            return true;
        } else {
            throw new Exception("결제 상세 정보 저장 실패");
        }
    }

    public List<PayDetailDTO> searchPurchaseLectures(String userId) {
        return userPaymentMapper.selectMyPurchaseList(userId);
    }
}