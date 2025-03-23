package com.vnpay.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "VnpayReturnController", value = "/VnpayReturnController")
public class VnpayReturn extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> fields = new HashMap<>();
        for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
            fields.put(entry.getKey(), entry.getValue()[0]);
        }

        String vnp_SecureHash = fields.get("vnp_SecureHash");
        fields.remove("vnp_SecureHash");

        // Sắp xếp các tham số theo thứ tự alphabet
        Set<String> keySet = fields.keySet();
        Iterator<String> itr = keySet.iterator();
        StringBuilder hashData = new StringBuilder();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName).append("=").append(fieldValue);
                if (itr.hasNext()) {
                    hashData.append("&");
                }
            }
        }

        // Tạo chữ ký kiểm tra
        String secureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());

        JsonObject jsonResponse = new JsonObject();
        if (secureHash.equals(vnp_SecureHash)) {
            String vnp_ResponseCode = fields.get("vnp_ResponseCode");
            if ("00".equals(vnp_ResponseCode)) {
                jsonResponse.addProperty("code", "00");
                jsonResponse.addProperty("message", "Giao dịch thành công!");
            } else {
                jsonResponse.addProperty("code", vnp_ResponseCode);
                jsonResponse.addProperty("message", "Giao dịch thất bại!");
            }
        } else {
            jsonResponse.addProperty("code", "97");
            jsonResponse.addProperty("message", "Chữ ký không hợp lệ!");
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(new Gson().toJson(jsonResponse));
    }
}
