package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.OrderModel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class OrderJsonUtil {
    // Hàm hiện có: Tạo JSON đơn giản hóa
    public static String generateSimplifiedOrderJson(OrderModel orderModel) {
        Map<String, Object> simpleOrder = new LinkedHashMap<>();
        simpleOrder.put("orderDate", orderModel.getOrderDate());
        simpleOrder.put("paymentName", orderModel.getPaymentName());
        simpleOrder.put("deliveryName", orderModel.getDeliveryName());
        simpleOrder.put("deliveryPrice", orderModel.getDeliveryPrice());
        simpleOrder.put("deliveryAddress", orderModel.getDeliveryAddress());
        simpleOrder.put("totalPrice", orderModel.getTotalPrice());
        simpleOrder.put("id", orderModel.getId());

        List<Map<String, Object>> productList = new ArrayList<>();
        orderModel.getProductModels().forEach(product -> {
            Map<String, Object> p = new LinkedHashMap<>();
            p.put("name", product.getName());
            p.put("price", product.getPrice());
            p.put("discount", product.getDiscount());
            p.put("quantity", product.getPurchaseQuantity());

            if (!product.getColorModels().isEmpty()) {
                var color = product.getColorModels().get(0);
                p.put("colorName", color.getName());
                p.put("colorHex", color.getHexCode());

                if (!color.getSizeModels().isEmpty()) {
                    p.put("size", color.getSizeModels().get(0).getSize());
                }
            }

            productList.add(p);
        });

        simpleOrder.put("products", productList);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(simpleOrder);
    }

    // Hàm hiện có: Tạo Base64 hash từ JSON đơn giản hóa
    public static String generateBase64HashFromOrder(OrderModel orderModel) {
        String orderJson = generateSimplifiedOrderJson(orderModel);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(orderJson.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported", e);
        }
    }

    // Hàm hiện có (đã sửa): Tạo JSON đầy đủ
    public static String generateFullOrderJson(OrderModel orderModel) {
        Map<String, Object> fullOrder = new LinkedHashMap<>();

        // Lấy tất cả thuộc tính của OrderModel
        fullOrder.put("id", orderModel.getId());
        fullOrder.put("orderDate", orderModel.getOrderDate());
        fullOrder.put("paymentName", orderModel.getPaymentName());
        fullOrder.put("deliveryName", orderModel.getDeliveryName());
        fullOrder.put("deliveryPrice", orderModel.getDeliveryPrice());
        fullOrder.put("deliveryAddress", orderModel.getDeliveryAddress());
        fullOrder.put("totalPrice", orderModel.getTotalPrice());
        fullOrder.put("base64Signature", orderModel.getSign()); // Sử dụng getSign()
        fullOrder.put("base64PublicKey", orderModel.getPublishKey()); // Sử dụng getPublishKey()

        // Lấy toàn bộ thông tin sản phẩm
        List<Map<String, Object>> productList = new ArrayList<>();
        orderModel.getProductModels().forEach(product -> {
            Map<String, Object> p = new LinkedHashMap<>();
            p.put("id", product.getId());
            p.put("name", product.getName());
            p.put("price", product.getPrice());
            p.put("discount", product.getDiscount());
            p.put("purchaseQuantity", product.getPurchaseQuantity());

            // Lấy toàn bộ thông tin màu sắc
            List<Map<String, Object>> colorList = new ArrayList<>();
            product.getColorModels().forEach(color -> {
                Map<String, Object> c = new LinkedHashMap<>();
                c.put("id", color.getId());
                c.put("name", color.getName());
                c.put("hexCode", color.getHexCode());

                // Lấy toàn bộ thông tin kích thước
                List<Map<String, Object>> sizeList = new ArrayList<>();
                color.getSizeModels().forEach(size -> {
                    Map<String, Object> s = new LinkedHashMap<>();
                    s.put("id", size.getId());
                    s.put("size", size.getSize());
                    sizeList.add(s);
                });

                c.put("sizes", sizeList);
                colorList.add(c);
            });

            p.put("colors", colorList);
            productList.add(p);
        });

        fullOrder.put("products", productList);

        // Sử dụng Gson để chuyển thành JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(fullOrder);
    }

    // Hàm mới: Tạo Base64 hash từ JSON đầy đủ
    public static String generateBase64HashFromFullOrder(OrderModel orderModel) {
        String orderJson = generateFullOrderJson(orderModel);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(orderJson.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported", e);
        }
    }

    // Hàm mới: Tạo mảng trạng thái chữ ký cho danh sách OrderModel
    public static String generateOrderSignStatusJson(List<OrderModel> orderModels) {
        List<Map<String, Object>> orderSignStatusList = new ArrayList<>();

        for (OrderModel orderModel : orderModels) {
            Map<String, Object> orderStatus = new LinkedHashMap<>();
            orderStatus.put("id", orderModel.getId());

            String sign = orderModel.getSign();
            String signStatus;

            if (sign == null || sign.trim().isEmpty()) {
                signStatus = "chưa ký";
            } else {
                try {
                    String hashBase64 = generateBase64HashFromOrder(orderModel); // Sử dụng hash từ JSON đơn giản hóa
                    String publicKey = orderModel.getPublishKey();
                    boolean isValid = SignatureVerifierUtil.verifySignature(hashBase64, sign, publicKey);
                    signStatus = isValid ? "ký đúng" : "ký sai";
                } catch (Exception e) {
                    signStatus = "ký sai"; // Xử lý lỗi xác minh chữ ký
                }
            }

            orderStatus.put("signStatus", signStatus);
            orderSignStatusList.add(orderStatus);
        }

        Gson gson = new GsonBuilder().create();
        return gson.toJson(orderSignStatusList);
    }
}