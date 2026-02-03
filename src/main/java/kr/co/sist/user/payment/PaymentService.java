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

    // 장바구니에 강의 추가
    public boolean addLectureToCart(String userId, String lectId) {
        boolean flag = false;
        
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("lectId", lectId);

        int count = userPaymentMapper.checkCartDuplicate(params);
        if (count == 0) {
            MyCartDTO mcDTO = new MyCartDTO();
            mcDTO.setUserId(userId);
            mcDTO.setLectId(lectId);
            
            int result = userPaymentMapper.insertCart(mcDTO);
            if(result > 0) flag = true;
        }
        return flag;
    }

    public List<MyCartDTO> getMyCart(String userId) {
        return userPaymentMapper.selectCartByUserId(userId);
    }

    public boolean deleteCartLectures(String userId, List<MyCartDTO> mcDTO) {
        boolean flag = false;
        int deleteCount = 0;

        for(MyCartDTO dto : mcDTO) {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("lectId", dto.getLectId());
            
            deleteCount += userPaymentMapper.deleteCartList(params);
        }
        
        if(deleteCount > 0) flag = true;
        return flag;
    }

 // PaymentService.java

    // [핵심] 강의 결제 (트랜잭션 처리)
    @Transactional(rollbackFor = Exception.class)
    public boolean purchaseLectures(String userId, List<MyCartDTO> mcDTO, int totalPrice) throws Exception {
        boolean flag = false;

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("totalPrice", totalPrice);

        // 1. PAYMENT_DETAIL (결제 헤더) 생성
        int resultRec = userPaymentMapper.insertPaymentRecord(params);
        String payRecId = (String) params.get("payRecId");

        if(resultRec > 0 && payRecId != null) {
            // 2. PAYMENT (결제 정보) 생성 - (Mapper XML에서 상태값 2로 변경 예정)
            userPaymentMapper.insertPayment(params);

            // 3. 각 강의별 처리
            for(MyCartDTO cartItem : mcDTO) {
                params.put("lectId", cartItem.getLectId());

                // [중요 수정] 이미 수강 중인 강의인지 확인!
                int existCount = userPaymentMapper.checkMyLectureDuplicate(params);
                
                if(existCount > 0) {
                    // 이미 수강 중인 강의라면 '수강 등록' 건너뜀 (또는 에러 발생시켜도 됨)
                    // 여기서는 장바구니에서만 삭제하고 넘어가는 것으로 처리
                    userPaymentMapper.deleteCartList(params);
                    continue; 
                }
                
                // 3-1. PAYMENT_DETAIL2 (결제 아이템) 생성
                userPaymentMapper.insertPaymentItem(params);

                // 3-2. 내 강의(수강) 등록
                userPaymentMapper.insertMyLecture(params);

                // 3-3. 강의의 수강생 수 증가
                userPaymentMapper.updateLectureUserCount(cartItem.getLectId());

                // 3-4. 장바구니에서 삭제
                userPaymentMapper.deleteCartList(params);
            }
            flag = true;
        } else {
            throw new Exception("결제 정보 저장 실패");
        }
        
        return flag;
    }

    public List<PayDetailDTO> searchPurchaseLectures(String userId) {
        return userPaymentMapper.selectMyPurchaseList(userId);
    }
}