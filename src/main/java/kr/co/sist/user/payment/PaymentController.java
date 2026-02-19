package kr.co.sist.user.payment;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user/payment")
@Controller("userPaymentController")
public class PaymentController {

    @Autowired
    private PaymentService ps;
    
//    @Value("${toss.secret-key}")
//    private String TOSS_SECRET_KEY;
    
    // 토스 시크릿 키 (개발자 센터에서 발급받은 키)
    private final String TOSS_SECRET_KEY = "test_sk_Ba5PzR0ArnOM6GO0vkYv8vmYnNeD"; 

    // 장바구니 담기
    @PostMapping("/addLectToCart")
    public String addLectToCart(String userId, String lectId) {
        ps.addLectureToCart(userId, lectId);
        return "redirect:/user/payment/searchMyCart"; 
    }//addLectToCart
    
    // 장바구니 목록 조회
    @GetMapping("/searchMyCart")
    public String searchMyCart(HttpSession session, Model model) {
        
        String userId = (String) session.getAttribute("userId");
        
        // userId없다면 user1으로 설정.
        if(userId == null) {
            userId = "user1"; 
            session.setAttribute("userId", userId);
        }//end if
        
        List<MyCartDTO> list = ps.getMyCart(userId);
        model.addAttribute("cartList", list);
        
        return "user/payment/cart"; 
    }//searchMyCart
    
    // 장바구니 항목 삭제
    @ResponseBody
    @GetMapping("/delLectFromCart")
    public String delLectFromCart(
            @RequestParam(value = "lectIds") List<String> lectIds, 
            HttpSession session) {
            
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "fail"; 
        }//end if

        try {
            // Service 메소드(deleteCartLectures)가 List<MyCartDTO>를 파라미터로 받으므로 변환
            List<MyCartDTO> list = new ArrayList<>();
            for (String lectId : lectIds) {
                MyCartDTO dto = new MyCartDTO();
                dto.setLectId(lectId); // ID만 담아서 보냄
                list.add(dto);
            }//end for

            // 기존 서비스 메소드 재사용
            boolean result = ps.deleteCartLectures(userId, list);
            
            return result ? "success" : "fail";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }//end catch
    }//purchaseLect
    
    // 구매 내역 조회
    @GetMapping("/searchMyPurchase")
    public String searchMyPurchase(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        
        if(userId == null) {
            userId = "user1"; // 테스트용 기본값
            session.setAttribute("userId", userId);
        }//end if

        List<PayDetailDTO> list = ps.searchPurchaseLectures(userId);
        model.addAttribute("purchaseList", list);
        
        return "user/payment/purchase_list"; 
    }//searchMyPurchase

    //토스 결제 성공 시 콜백 핸들러 (API 승인 요청 포함.)
    @GetMapping("/toss/success")
    public String tossSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount, // 금액 : Long 타입으로 함
            @RequestParam(name = "lectIds", required = false) String lectIds,
            HttpSession session, Model model) {
        
        String userId = (String) session.getAttribute("userId");
        if(userId == null) userId = "user1"; //임시방편:세션없다면 user1으로 설정.(후에 수정할 것)

        try {
            //토스 서버로 최종 승인 요청
            String responseStr = confirmPayment(paymentKey, orderId, amount);
            
            //응답 확인 (JSON 파싱)
            JSONParser parser = new JSONParser();
            JSONObject response = (JSONObject) parser.parse(responseStr);
            
            //DB 처리. 선택된 강의 목록 구성
            List<MyCartDTO> allCartList = ps.getMyCart(userId);
            List<MyCartDTO> targetList = new ArrayList<>();

            if (lectIds != null && !lectIds.isEmpty()) {
                String[] selectedIdArr = lectIds.split(",");
                for (MyCartDTO dto : allCartList) {
                    for (String selId : selectedIdArr) {
                        if (dto.getLectId().equals(selId)) {
                            targetList.add(dto);
                            break; 
                        }//end if
                    }//end for
                }//end for
            } else {
                targetList = allCartList; // 파라미터 없으면 전체 처리
            }//end if

            // DB 결제 정보 저장 및 장바구니 삭제
            ps.purchaseLectures(userId, targetList, amount.intValue());
            
            return "user/payment/success_page";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "결제 승인 중 오류가 발생했습니다: " + e.getMessage());
            return "user/payment/fail_page";
        }//end catch
    }//end if

    //토스 API 호출 메소드
    private String confirmPayment(String paymentKey, String orderId, Long amount) throws Exception {
    	System.out.println(">>> 현재 적용된 시크릿 키: " + TOSS_SECRET_KEY);
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((TOSS_SECRET_KEY + ":").getBytes(StandardCharsets.UTF_8)));
        conn.setDoOutput(true);

        String jsonBody = "{\"paymentKey\":\"" + paymentKey + "\",\"orderId\":\"" + orderId + "\",\"amount\":" + amount + "}";
        
        try(OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        }//end try
        
        // 응답 코드 확인
        int code = conn.getResponseCode();
        boolean isSuccess = code == 200;
        
        InputStream responseStream = isSuccess ? conn.getInputStream() : conn.getErrorStream();
        
        // 응답 읽기
        BufferedReader br = new BufferedReader(new InputStreamReader(responseStream, StandardCharsets.UTF_8));
        StringBuilder res = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            res.append(line);
        }//end while
        br.close();
        
        // 실패 시 예외 발생시켜서 catch 블록으로 이동
        if(!isSuccess) {
            throw new RuntimeException("토스 승인 실패: " + res.toString());
        }//end if
        
        return res.toString();
    }//confirmPayment
    
    
}//class