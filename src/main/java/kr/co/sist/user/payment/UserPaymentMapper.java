package kr.co.sist.user.payment;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

@Mapper
public interface UserPaymentMapper {

    // 장바구니 추가
    int insertCart(MyCartDTO mcDTO) throws PersistenceException;

    // 장바구니 목록 조회
    List<MyCartDTO> selectCartByUserId(String userId) throws PersistenceException;

    // 장바구니 삭제
    int deleteCartList(Map<String, Object> params) throws PersistenceException;

    // 장바구니 중복 확인
    int checkCartDuplicate(Map<String, String> params) throws PersistenceException;

    // [1] 결제 헤더 생성 (PAYMENT_DETAIL)
    int insertPaymentRecord(Map<String, Object> params) throws PersistenceException;

    // [2] 결제 정보 생성 (PAYMENT)
    int insertPayment(Map<String, Object> params) throws PersistenceException;

    // [3] 결제 아이템 생성 (PAYMENT_DETAIL2)
    int insertPaymentItem(Map<String, Object> params) throws PersistenceException;

    // 수강 테이블 추가
    int insertMyLecture(Map<String, Object> params) throws PersistenceException;

    // 강의 수강생 수 증가
    int updateLectureUserCount(String lectId) throws PersistenceException;
    
    int checkMyLectureDuplicate(Map<String, Object> params) throws PersistenceException;

    // 내 구매 내역 조회
    List<PayDetailDTO> selectMyPurchaseList(String userId) throws PersistenceException;
}