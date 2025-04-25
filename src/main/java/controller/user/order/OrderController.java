package controller.user.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.*;
import service.log.LogService;
import service.user.order.OrderService;
import service.user.cart.ShoppingCartItemOrderService;
import service.user.cart.ShoppingCartService;
import service.user.voucher.VoucherService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "OrderController", value = "/OrderController")
public class OrderController extends HttpServlet {
    private final VoucherService voucherService = new VoucherService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("signIn.jsp");
            return;
        }

        OrderService orderService = new OrderService();
        try {
            // Lấy danh sách đơn hàng của user
            List<OrderModel> orders = orderService.getAllOrders(user.getId());

            // Sắp xếp theo ID giảm dần
            orders.sort(Comparator.comparingInt(OrderModel::getId).reversed());

            request.setAttribute("listOrder", orders);
            request.getRequestDispatcher("statusShoes.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"false\",\"message\":\"Failed to retrieve orders.\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String ipAddress = request.getRemoteAddr();

        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        if (user == null) {
            response.getWriter().write("{\"status\":\"false\",\"message\":\"User not logged in.\"}");
            return;
        }

        try {
            // Thu thập dữ liệu từ form
            int paymentId = Integer.parseInt(request.getParameter("paymentId"));
            int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));

            // Lấy giá trị của selectedVoucherShipping và selectedVoucherItems
            String selectedVoucherShipping = request.getParameter("selectedVoucherShipping");
            String selectedVoucherItems = request.getParameter("selectedVoucherItems");

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

            List<Integer> selectedItems = parseSelectedItems(request.getParameter("selectedItems"));
            Timestamp sqlTimestamp = Timestamp.valueOf(LocalDateTime.now());

            // Tạo đối tượng Order
            OrderService orderService = new OrderService();

            // Tính toán tổng giá
            float discountShippingFee = 0;
            float discountItemsFee = 0;
            discountShippingFee = voucherService.calculateDiscountShippingFee(voucherShippingId, deliveryId);
            discountItemsFee = voucherService.calculateDiscountItemsFee(voucherItemsId, selectedItems);

            float totalPrice = orderService.calculateTotalPrice(selectedItems, user.getId(), deliveryId) - discountShippingFee - discountItemsFee;
            Order order = new Order(paymentId, sqlTimestamp, request.getParameter("address"), totalPrice, user.getId(),
                    deliveryId);

            // Xử lý đặt hàng
            ShoppingCartService shoppingCartService = new ShoppingCartService();
            ShoppingCartItemOrderService cartItemOrderService = new ShoppingCartItemOrderService();

            shoppingCartService.updateStockProduct(user.getId());

            int orderId = orderService.addOrder(order);

            cartItemOrderService.addShoppingCartItemOrders(selectedItems, user.getId(), orderId);
            shoppingCartService.cleanShoppingCartItems(selectedItems);

            // add status

            switch (paymentId) {
                case 1:
                    response.sendRedirect(request.getContextPath() + "/OrderController");
                    LogService.paymentCod(user.getId(), String.valueOf(orderId),ipAddress);
                    break;
                case 2:
                    response.sendRedirect("https://sandbox.vnpayment.vn/apis/");
                    break;
                default:
                    response.getWriter().write("{\"status\":\"false\",\"message\":\"Invalid payment method.\"}");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"false\",\"message\":\"Failed to place order.\"}");
        }
    }

    // Utility method to parse a float from a string with a default value
    private float parseFloatOrDefault(String value, float defaultValue) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
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
