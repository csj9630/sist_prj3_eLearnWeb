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
    }
    
    // 장바구니 목록 조회
    @GetMapping("/searchMyCart")
    public String searchMyCart(HttpSession session, Model model) {
        // [수정] 하드코딩 삭제 및 세션 체크 로직 적용
        String userId = (String) session.getAttribute("userId");
        
        // 로그인 안 되어 있으면 테스트용 user4 자동 설정 (UserDashboardController와 동일한 방식)
        if(userId == null) {
            userId = "user4"; 
            session.setAttribute("userId", userId);
        }
        
        List<MyCartDTO> list = ps.getMyCart(userId);
        model.addAttribute("cartList", list);
        
        return "user/payment/cart"; 
    }
    
    // 장바구니 항목 삭제
    @GetMapping("/delLectFromCart")
    public String delLectFromCart(String lectId, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null) return "redirect:/user/payment/searchMyCart"; // 방어 로직
        
        MyCartDTO dto = new MyCartDTO();
        dto.setLectId(lectId);
        List<MyCartDTO> list = new ArrayList<>();
        list.add(dto);
        
        ps.deleteCartLectures(userId, list);
        
        return "redirect:/user/payment/searchMyCart"; 
    }
    
    // 결제 과정 페이지 (필요 시 사용)
    @PostMapping("/purchaseLect")
    public String purchaseLect(String userId, Model model) {
        return "user/payment/payment_process"; 
    }
    
    // 구매 내역 조회
    @GetMapping("/searchMyPurchase")
    public String searchMyPurchase(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        
        if(userId == null) {
            userId = "user4"; // 테스트용 기본값
            session.setAttribute("userId", userId);
        }

        List<PayDetailDTO> list = ps.searchPurchaseLectures(userId);
        model.addAttribute("purchaseList", list);
        
        return "user/payment/purchase_list"; 
    }

    // ----------------------------------------------------------------
    // [중요] 토스 결제 성공 시 콜백 핸들러 (API 승인 요청 포함)
    // ----------------------------------------------------------------
    @GetMapping("/toss/success")
    public String tossSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount, // 금액은 Long 타입 권장
            @RequestParam(name = "lectIds", required = false) String lectIds,
            HttpSession session, Model model) {
        
        String userId = (String) session.getAttribute("userId");
        if(userId == null) userId = "user4"; // 세션 만료 방지용 테스트값

        try {
            // 1. [토스 서버로 최종 승인 요청] (이게 없으면 실제 결제가 안 된 것임)
            String responseStr = confirmPayment(paymentKey, orderId, amount);
            
            // 2. 응답 확인 (JSON 파싱)
            JSONParser parser = new JSONParser();
            JSONObject response = (JSONObject) parser.parse(responseStr);
            
            // 토스 응답에 "status"가 "DONE"인지 확인하면 더 정확하지만, 
            // confirmPayment에서 에러가 안 나면 성공으로 간주하고 진행합니다.

            // 3. DB 처리: 선택된 강의 목록 구성
            List<MyCartDTO> allCartList = ps.getMyCart(userId);
            List<MyCartDTO> targetList = new ArrayList<>();

            if (lectIds != null && !lectIds.isEmpty()) {
                String[] selectedIdArr = lectIds.split(",");
                for (MyCartDTO dto : allCartList) {
                    for (String selId : selectedIdArr) {
                        if (dto.getLectId().equals(selId)) {
                            targetList.add(dto);
                            break; 
                        }
                    }
                }
            } else {
                targetList = allCartList; // 파라미터 없으면 전체 처리
            }

            // 4. DB 결제 정보 저장 및 장바구니 삭제
            // (int로 캐스팅하여 전달)
            ps.purchaseLectures(userId, targetList, amount.intValue());
            
            return "user/payment/success_page";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "결제 승인 중 오류가 발생했습니다: " + e.getMessage());
            return "user/payment/fail_page";
        }
    }

    // [보조] 토스 API 호출 메소드 (실제 구현부)
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
        }
        
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
        }
        br.close();
        
        // 실패 시 예외 발생시켜서 catch 블록으로 이동시킴
        if(!isSuccess) {
            throw new RuntimeException("토스 승인 실패: " + res.toString());
        }
        
        return res.toString();
    }
}