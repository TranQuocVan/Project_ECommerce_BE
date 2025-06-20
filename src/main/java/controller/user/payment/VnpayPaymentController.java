/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.user.payment;

import controller.user.payment.config.Config;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import model.Order;
import model.UserModel;
import service.log.LogService;
import service.user.cart.ShoppingCartItemOrderService;
import service.user.cart.ShoppingCartService;
import service.user.order.OrderService;

import java.io.IOException;import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.user.voucher.VoucherService;

/**
 *
 * @author CTT VNPAY
 */
@WebServlet(name = "VnpayPaymentController", value = "/VnpayPaymentController")
public class VnpayPaymentController extends HttpServlet {
    private final VoucherService voucherService = new VoucherService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String ipAddress = req.getRemoteAddr();

        HttpSession session = req.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        if (user == null) {
            resp.getWriter().write("{\"status\":\"false\",\"message\":\"User not logged in.\"}");
            return;
        }

        float totalPrice = 0;
        int orderId = 0;
        try {
            // Thu thập dữ liệu từ form
            int paymentId = Integer.parseInt(req.getParameter("paymentId"));
            int deliveryId = Integer.parseInt(req.getParameter("deliveryId"));

            // Lấy giá trị của selectedVoucherShipping và selectedVoucherItems
            String selectedVoucherShipping = req.getParameter("selectedVoucherShipping");
            String selectedVoucherItems = req.getParameter("selectedVoucherItems");

            // Nếu không có voucher nào được chọn, có thể trả về lỗi hoặc xử lý theo yêu cầu
            if (selectedVoucherShipping == null || selectedVoucherShipping.isEmpty()) {
                selectedVoucherShipping = "0";  // Hoặc có thể trả về lỗi nếu cần
            }
            if (selectedVoucherItems == null || selectedVoucherItems.isEmpty()) {
                selectedVoucherItems = "0";  // Hoặc có thể trả về lỗi nếu cần
            }

            // Chuyển thành Integer nếu cần thiết, hoặc có thể xử lý theo cách khác
            int voucherShippingId = Integer.parseInt(selectedVoucherShipping);
            int voucherItemsId = Integer.parseInt(selectedVoucherItems);

            List<Integer> voucherIdsToDecrease = new ArrayList<>();

            if (voucherShippingId != 0) {
                voucherIdsToDecrease.add(voucherShippingId);
            }
            if (voucherItemsId != 0) {
                voucherIdsToDecrease.add(voucherItemsId);
            }

            boolean isVoucherQuantityDecreased = true;
            if (!voucherIdsToDecrease.isEmpty()) {
                isVoucherQuantityDecreased = voucherService.decreaseVoucherQuantity(voucherIdsToDecrease);
                LogService.voucherDecreaseQuantity(user.getId(), isVoucherQuantityDecreased, ipAddress);
            }

            List<Integer> selectedItems = parseSelectedItems(req.getParameter("selectedItems"));
            Timestamp sqlTimestamp = Timestamp.valueOf(LocalDateTime.now());

            // Tạo đối tượng Order
            OrderService orderService = new OrderService();

            // Tính toán tổng giá
            float discountShippingFee = 0;
            float discountItemsFee = 0;
            float shippingFee = 0;
            discountShippingFee = voucherService.calculateDiscountShippingFee(voucherShippingId, deliveryId);
            discountItemsFee = voucherService.calculateDiscountItemsFee(voucherItemsId, selectedItems);
            try {
                shippingFee = Float.parseFloat(req.getParameter("shippingFee"));
            } catch (NumberFormatException e) {
                shippingFee = 0;
            }

//            totalPrice = orderService.calculateTotalPrice(selectedItems, user.getId(), deliveryId) - discountShippingFee - discountItemsFee;
            totalPrice = orderService.calculateTotalPrice(selectedItems, user.getId(), deliveryId)
                    - discountShippingFee - discountItemsFee + shippingFee;
            System.out.println("Tổng giá: " + totalPrice);
            Order order = new Order(paymentId, sqlTimestamp, req.getParameter("address"), totalPrice, user.getId(), deliveryId);

            // Xử lý đặt hàng
            ShoppingCartService shoppingCartService = new ShoppingCartService();
            ShoppingCartItemOrderService cartItemOrderService = new ShoppingCartItemOrderService();

            shoppingCartService.updateStockProduct(user.getId());

            orderId = orderService.addOrder(order);
            System.out.println("OrderId: " + orderId);
            if (orderId <= 0) {
                resp.getWriter().write("{\"status\":\"false\",\"message\":\"Failed to create order.\"}");
                return;
            }

            cartItemOrderService.addShoppingCartItemOrders(selectedItems, user.getId(), orderId);
            shoppingCartService.cleanShoppingCartItems(selectedItems);

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"status\":\"false\",\"message\":\"Failed to place order.\"}");
        }

        double amountDouble = (double) (totalPrice * 100);
        
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = (long) amountDouble;
        String bankCode = req.getParameter("bankCode");

//        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_TxnRef = String.valueOf(orderId);
//        orderService.updateTransactionRef(orderId, vnp_TxnRef);

        String vnp_IpAddr = Config.getIpAddress(req);

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        System.out.println("Redirecting to: " + paymentUrl);
        resp.sendRedirect(paymentUrl);
//        JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));
    }

    // Utility method to parse selected items from a comma-separated string
    private List<Integer> parseSelectedItems(String selectedItemsParam) {
        List<Integer> selectedItems = new ArrayList<>();
        if (selectedItemsParam != null && !selectedItemsParam.isEmpty()) {
            String[] items = selectedItemsParam.split(",");
            for (String item : items) {
                try {
                    selectedItems.add(Integer.parseInt(item.trim()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return selectedItems;
    }
}
