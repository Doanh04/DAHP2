package com.Phamducdoanh.Backend.VNPay;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class VNPayUltil {
    public String createPaymentUrl(String transactionId, BigDecimal total, HttpServletRequest request) throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", Config.vnp_TmnCode);
        params.put("vnp_Amount", total.multiply(BigDecimal.valueOf(100)).longValue() + "");
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", transactionId);
        params.put("vnp_OrderInfo", "Thanh toan don hang " + transactionId);
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        params.put("vnp_IpAddr", Config.getIpAddress(request));

        Calendar cld =  Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_createDate = formatter.format(cld.getTime());
        params.put("vnp_CreateDate", vnp_createDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fielNames = new ArrayList(params.keySet());
        Collections.sort(fielNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fielNames.iterator();

        while (itr.hasNext()) {
            String fielName = (String) itr.next();
            String fielValue = (String) params.get(fielName);

            if((fielName!=null)&&(fielValue.length()>0)){
//                build hashdata
                hashData.append(fielName);
                hashData.append("=");
                hashData.append(URLEncoder.encode(fielValue, StandardCharsets.US_ASCII.toString()));
//                build query
                query.append(URLEncoder.encode(fielName, StandardCharsets.US_ASCII.toString()));
                query.append("=");
                query.append(URLEncoder.encode(fielValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append("&");
                    hashData.append("&");
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return Config.vnp_PayUrl +"?" + queryUrl;
    }

    public boolean verifySignature(Map<String, String> params, String vnp_SecureHash) {
        try {
            // Sort params theo thứ tự alphabet
            SortedMap<String, String> sorted = new TreeMap<>(params);

            // Build hashData giống hệt như lúc tạo payment URL
            StringBuilder hashData = new StringBuilder();
            for (Map.Entry<String, String> entry : sorted.entrySet()) {
                String fieldName = entry.getKey();
                String fieldValue = entry.getValue();

                // Chỉ lấy các field vnp_ (trừ vnp_SecureHash và vnp_SecureHashType)
                if (fieldName.startsWith("vnp_") && fieldValue != null && !fieldValue.isEmpty()) {
                    if (hashData.length() > 0) {
                        hashData.append("&");
                    }
                    hashData.append(fieldName).append("=").append(fieldValue);
                }
            }
            // Hash với secret key
            String calculatedHash = Config.hmacSHA512(Config.secretKey, hashData.toString());


            return calculatedHash.equalsIgnoreCase(vnp_SecureHash);

        } catch (Exception e) {
           // log.error("Error verifying signature: ", e);
            return false;
        }
    }


}
