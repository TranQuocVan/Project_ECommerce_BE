package controller.user.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;
import model.OrderModel;
import model.UserModel;
import service.log.LogService;
import service.user.cart.ShoppingCartItemOrderService;
import service.user.cart.ShoppingCartService;
import service.user.order.OrderService;
import service.user.voucher.VoucherService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import util.SignatureVerifier;


@WebServlet(name = "OrderController", value = "/OrderController")
public class OrderController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());
    private static final String STATUS_PAGE = "statusShoes.jsp";
    private static final String LOGIN_PAGE = "login.jsp";
    private final OrderService orderService = new OrderService();
    private final VoucherService voucherService = new VoucherService();
    private final ShoppingCartService shoppingCartService = new ShoppingCartService();
    private final ShoppingCartItemOrderService cartItemOrderService = new ShoppingCartItemOrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/" + LOGIN_PAGE);
            return;
        }

        try {
            List<OrderModel> orders = orderService.getAllOrders(user.getId());
            orders.sort(Comparator.comparingInt(OrderModel::getId).reversed());
            request.setAttribute("listOrder", orders);
            request.getRequestDispatcher(STATUS_PAGE).forward(request, response);
        } catch (Exception e) {
            LOGGER.severe("Failed to retrieve orders: " + e.getMessage());
            sendErrorResponse(response, "Failed to retrieve orders.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("user") : null;

        if (user == null) {
            sendErrorResponse(response, "User not logged in.");
            return;
        }

        try {
            // Extract and validate form data
            int paymentId = parseIntParameter(request, "paymentId", -1);
            int deliveryId = parseIntParameter(request, "deliveryId", -1);
            String publishKey = request.getParameter("publishKey");
            String hash = request.getParameter("hash");
            String data = request.getParameter("data");
            String address = request.getParameter("address");



//             Verify digital signature
            if (!SignatureVerifier.verifySignature(data, hash, publishKey)) {
                request.setAttribute("orderError", "Signature verification failed.");
                request.getRequestDispatcher("/shoppingCart.jsp").forward(request, response);
            }

            // Parse voucher IDs with default value of 0 if not provided
            int voucherShippingId = parseIntParameter(request, "selectedVoucherShipping", 0);
            int voucherItemsId = parseIntParameter(request, "selectedVoucherItems", 0);
            List<Integer> selectedItems = parseSelectedItems(request.getParameter("selectedItems"));

            if (selectedItems.isEmpty()) {
                sendErrorResponse(response, "No items selected for the order.");
                return;
            }

            // Calculate discounts and total price
            float discountShippingFee = voucherService.calculateDiscountShippingFee(voucherShippingId, deliveryId);
            float discountItemsFee = voucherService.calculateDiscountItemsFee(voucherItemsId, selectedItems);
            float totalPrice = orderService.calculateTotalPrice(selectedItems, user.getId(), deliveryId)
                    - discountShippingFee - discountItemsFee;

            // Create and save order
            Order order = new Order(
                    paymentId,
                    Timestamp.valueOf(LocalDateTime.now()),
                    address,
                    totalPrice,
                    user.getId(),
                    deliveryId,
                    publishKey,
                    hash
            );

            int orderId = orderService.addOrder(order);
            cartItemOrderService.addShoppingCartItemOrders(selectedItems, user.getId(), orderId);
            shoppingCartService.updateStockProduct(user.getId());
            shoppingCartService.cleanShoppingCartItems(selectedItems);

            // Log and redirect based on payment method
            String ipAddress = request.getRemoteAddr();

            LogService.paymentCod(user.getId(), String.valueOf(orderId), ipAddress);
            response.sendRedirect(request.getContextPath() + "/OrderController");

        } catch (Exception e) {
            LOGGER.severe("Failed to place order: " + e.getMessage());
            sendErrorResponse(response, "Failed to place order.");
        }
    }

    // Utility method to send JSON error response
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.getWriter().write(String.format("{\"status\":\"false\",\"message\":\"%s\"}", message));
    }

    // Utility method to parse integer parameters with a default value
    private int parseIntParameter(HttpServletRequest request, String paramName, int defaultValue) {
        String paramValue = request.getParameter(paramName);
        try {
            return (paramValue != null && !paramValue.isEmpty()) ? Integer.parseInt(paramValue) : defaultValue;
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid integer format for parameter " + paramName + ": " + paramValue);
            return defaultValue;
        }
    }

    // Utility method to parse selected items from a comma-separated string
    private List<Integer> parseSelectedItems(String selectedItemsParam) {
        List<Integer> selectedItems = new ArrayList<>();
        if (selectedItemsParam != null && !selectedItemsParam.isEmpty()) {
            for (String item : selectedItemsParam.split(",")) {
                try {
                    selectedItems.add(Integer.parseInt(item.trim()));
                } catch (NumberFormatException e) {
                    LOGGER.warning("Invalid item ID: " + item);
                }
            }
        }
        return selectedItems;
    }
}