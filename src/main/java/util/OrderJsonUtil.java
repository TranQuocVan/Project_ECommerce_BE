package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.OrderModel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class OrderJsonUtil {
    public static String generateSimplifiedOrderJson(OrderModel orderModel) {
        Map<String, Object> simpleOrder = new LinkedHashMap<>();
        simpleOrder.put("orderDate", orderModel.getOrderDate());
        simpleOrder.put("paymentName", orderModel.getPaymentName());
        simpleOrder.put("deliveryName", orderModel.getDeliveryName());
        simpleOrder.put("deliveryPrice", orderModel.getDeliveryPrice());
        simpleOrder.put("deliveryAddress", orderModel.getDeliveryAddress());
        simpleOrder.put("totalPrice", orderModel.getTotalPrice());
        simpleOrder.put("id",orderModel.getId());

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
}