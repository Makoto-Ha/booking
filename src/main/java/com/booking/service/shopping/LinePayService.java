package com.booking.service.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.booking.bean.dto.shopping.PayDetailDTO;
import com.booking.bean.dto.shopping.ShopOrderDTO;
import com.booking.config.NgrokUrlConfig;
import com.booking.dao.shopping.ShopOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class LinePayService {

    @Value("${linepay.channel_id}")
    private String channelId;

    @Value("${linepay.channel_secret}")
    private String channelSecret;

    private static final String API_URL = "https://sandbox-api-pay.line.me/v3/payments/request";
    private static final String API_PATH = "/v3/payments/request"; // API路徑

    @Autowired
    private ShopOrderRepository shopOrderRepository;
    @Autowired
    private NgrokUrlConfig ngrokUrlConfig;

    /**
     * 請求支付
     * @param orderDTO
     * @param userId
     * @param payDetailDTO
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public String requestPayment(ShopOrderDTO orderDTO, String userId, PayDetailDTO payDetailDTO) throws Exception {
    	// 使用 RestTemplate 發送 HTTP 請求
    	RestTemplate restTemplate = new RestTemplate();

        String merchantTradeNo = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        orderDTO.setMerchantTradeNo(merchantTradeNo);

        Map<String, Object> request = new HashMap<>();
        request.put("amount", orderDTO.getOrderPrice());
        request.put("currency", "TWD");
        request.put("orderId", merchantTradeNo);
        request.put("packages", getPackages(orderDTO));
        request.put("redirectUrls", getRedirectUrls());

        // 自訂參數
        Map<String, String> customParameters = new HashMap<>();
        customParameters.put("myOrderId", orderDTO.getOrderId().toString());
        customParameters.put("userId", userId);
        request.put("customParameters", customParameters);

        String requestBody = new ObjectMapper().writeValueAsString(request);

        // 設定 Headers
        String nonce = UUID.randomUUID().toString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-LINE-ChannelId", channelId);
        headers.set("X-LINE-Authorization-Nonce", nonce);
        headers.set("X-LINE-Authorization", generateSignature(API_PATH, requestBody, channelSecret, nonce));

        // 更新訂單資訊
        shopOrderRepository.findById(orderDTO.getOrderId()).ifPresent(order -> {
            order.setMerchantTradeNo(merchantTradeNo);
            order.setPaymentMethod(2);
            order.setReceiverName(payDetailDTO.getReceiverName());
            order.setReceiverPhone(Integer.parseInt(payDetailDTO.getReceiverPhone()));
            order.setReceiverAddress(payDetailDTO.getReceiverAddress());
            shopOrderRepository.save(order);
        });

        System.out.println("headers====" + headers);
        System.out.println("requestBody=====" + requestBody);

        // 發送請求
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = new ObjectMapper().readValue(response.getBody(), Map.class);
            Map<String, Object> info = (Map<String, Object>) responseBody.get("info");
            Map<String, String> paymentUrlMap = (Map<String, String>) info.get("paymentUrl");
            return paymentUrlMap.get("web");  // 返回支付跳轉 URL
        } else {
            throw new Exception("LinePay API request failed");
        }
    }

    /**
     * 組裝重定向 URL
     * @return
     */
    private Map<String, String> getRedirectUrls() {
        Map<String, String> redirectUrls = new HashMap<>();
        redirectUrls.put("confirmUrl", "http://localhost:8080/booking/shop/checkout/linepay/confirm");
        redirectUrls.put("cancelUrl", "http://localhost:8080/booking/shop/cart");
        return redirectUrls;
    }

    /**
     * 組裝商品清單
     * @param orderDTO
     * @return
     */
    private List<Map<String, Object>> getPackages(ShopOrderDTO orderDTO) {
        Map<String, Object> packageData = new HashMap<>();
        packageData.put("id", orderDTO.getMerchantTradeNo()); // 包裝 ID
        packageData.put("amount", orderDTO.getOrderPrice()); // 總金額
        packageData.put("currency", "TWD"); // 貨幣
        packageData.put("name", "商品付款"); // 包裝名稱

        List<Map<String, Object>> products = orderDTO.getOrderItems().stream().map(item -> {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("name", item.getProductName()); // 商品名稱
            itemMap.put("quantity", item.getQuantity()); // 商品數量
            itemMap.put("price", item.getPrice()); // 單價
            return itemMap;
        }).collect(Collectors.toList());

        packageData.put("products", products); // 商品清單 (使用 "products" 而非 "items")
        return List.of(packageData);
    }

    /**
     * 生成簽名
     * @param apiPath
     * @param requestBody
     * @param channelSecret
     * @param nonce
     * @return
     * @throws Exception
     */
    public String generateSignature(String apiPath, String requestBody, String channelSecret, String nonce) throws Exception {
        String signatureString = channelSecret + apiPath + requestBody + nonce;
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(channelSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256HMAC.init(secretKey);
        byte[] hmacData = sha256HMAC.doFinal(signatureString.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hmacData);
    }
}