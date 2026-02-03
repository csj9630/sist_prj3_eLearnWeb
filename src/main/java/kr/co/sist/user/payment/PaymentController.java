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
    
    // 토스 시크릿 키 (개발자 센터에서 발급받은 키로 변경)
    private final String TOSS_SECRET_KEY = "test_sk_Ba5PzR0ArnOM6GO0vkYv8vmYnNeD"; 

    @PostMapping("/addLectToCart")
    public String addLectToCart(String userId, String lectId) {
        ps.addLectureToCart(userId, lectId);
        return "redirect:/user/payment/searchMyCart"; // 장바구니 페이지로 이동
    }//addLectToCart
    
    @GetMapping("/searchMyCart")
    public String searchMyCart(HttpSession session, Model model) {
    	
    	//테스트용(로그인 미리 한 코드. 테스트 끝나고 반드시 지울 것!!!!!!!!!!!)
    	session.setAttribute("userId", "user1"); 
        session.setAttribute("userName", "이정우");
    	//테스트용
        
        String userId = (String) session.getAttribute("userId");
        if(userId != null) {
            List<MyCartDTO> list = ps.getMyCart(userId);
            model.addAttribute("cartList", list);
        }
        return "user/payment/cart"; // 장바구니 뷰 페이지
    }//searchMyCart
    
    @GetMapping("/user/payment/delLectFromCart")
    public String delLectFromCart(String lectId, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        
        // 단일 삭제를 위한 리스트 포장
        MyCartDTO dto = new MyCartDTO();
        dto.setLectId(lectId);
        List<MyCartDTO> list = new ArrayList<>();
        list.add(dto);
        
        // 기존 서비스 메서드 재활용
        ps.deleteCartLectures(userId, list);
        
        return "redirect:/user/payment/searchMyCart"; // 장바구니로 다시 리다이렉트
    }
    
    // 기존에 만드신 메소드 (결제하기 버튼 클릭 시 동작 등으로 활용)
    @PostMapping("/purchaseLect")
    public String purchaseLect(String userId, Model model) {
        return "user/payment/payment_process"; 
    }//purchaseLect
    
    @GetMapping("/searchMyPurchase")
    public String searchMyPurchase(HttpSession session, Model model) {
        // [수정] 파라미터로 받는 게 아니라, 세션에서 꺼내옵니다.
        String userId = (String) session.getAttribute("userId");
        
        // 아이디가 없으면(로그인 안했으면) 빈 목록 또는 리다이렉트
        if(userId == null) {
            return "redirect:/";
        }

        List<PayDetailDTO> list = ps.searchPurchaseLectures(userId);
        model.addAttribute("purchaseList", list);
        
        return "user/payment/purchase_list"; // 이 HTML 파일이 필요합니다!
    }//searchMyPurchase

    // ----------------------------------------------------------------
    // [추가] 토스 결제 성공 시 리다이렉트되는 URL 핸들러
    // ----------------------------------------------------------------

    @GetMapping("/toss/success")
    public String tossSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam int amount,
            @RequestParam(name = "lectIds", required = false) String lectIds, // [추가] 선택된 ID들 받기
            HttpSession session, Model model) {
        
        String userId = (String) session.getAttribute("userId");
        
        try {
            // 1. 전체 장바구니 목록 가져오기
            List<MyCartDTO> allCartList = ps.getMyCart(userId);
            List<MyCartDTO> targetList = new ArrayList<>();

            // 2. 선택된 강의만 필터링 (lectIds가 있으면 그것만, 없으면 전체)
            if (lectIds != null && !lectIds.isEmpty()) {
                String[] selectedIdArr = lectIds.split(",");
                for (MyCartDTO dto : allCartList) {
                    for (String selId : selectedIdArr) {
                        if (dto.getLectId().equals(selId)) {
                            targetList.add(dto);
                            break; // 찾았으면 다음 ID로
                        }
                    }
                }
            } else {
                targetList = allCartList; // 예외적으로 파라미터가 없으면 전체 결제
            }

            // 3. 필터링된 목록(targetList)만 결제 진행
            // (Service는 수정할 필요 없이 리스트에 있는 것만 결제하고 삭제함)
            ps.purchaseLectures(userId, targetList, amount);
            
            return "user/payment/success_page";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "결제 승인 중 오류가 발생했습니다.");
            return "user/payment/fail_page";
        }
    }

    // [보조] 토스 API 호출 메소드
    private String confirmPayment(String paymentKey, String orderId, Long amount) throws Exception {
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
        
        // 응답 읽기 (생략 없이 전체 구현 필요 시 이전 답변의 코드 참고)
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getResponseCode() == 200 ? conn.getInputStream() : conn.getErrorStream()));
        StringBuilder res = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) res.append(line);
        return res.toString();
    }
}