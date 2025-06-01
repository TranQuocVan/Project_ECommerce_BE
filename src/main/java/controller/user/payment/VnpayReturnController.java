package controller.user.payment;

import java.io.IOException;
import java.io.PrintWriter;

import controller.user.payment.config.Config;
import database.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import service.log.LogService;
import service.user.account.UserService;
import service.user.cart.ShoppingCartService;
import service.user.order.OrderService;

@WebServlet(name = "VnpayReturnController", value = "/VnpayReturnController")
public class VnpayReturnController extends HttpServlet {
//    OrderDao orderDao = new OrderDao();
    private final OrderService orderService = new OrderService();
    private final ShoppingCartService shoppingCartService = new ShoppingCartService();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String ipAddress = request.getRemoteAddr();
        try ( PrintWriter out = response.getWriter()) {
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = Config.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash)) {
//                String paymentCode = request.getParameter("vnp_TransactionNo");

                String orderId = request.getParameter("vnp_TxnRef");

                int orderIdInt = Integer.parseInt(orderId);
                boolean transSuccess = false;

                int statusPayment = 0;
                String transactionStatus = request.getParameter("vnp_TransactionStatus");
                if ("00".equals(transactionStatus)) {
                    //update banking system
                    statusPayment = 1;
//                    order.setStatus("Completed");
                    transSuccess = true;
                } else {
                    // Trả lại hàng vào kho trước khi xóa đơn
                    boolean restored = shoppingCartService.restoreStockByOrderId(orderIdInt);
                    if (!restored) {
                        System.out.println("Khôi phục tồn kho thất bại! Không xóa đơn hàng.");
                        response.getWriter().write("{\"status\":\"false\",\"message\":\"Restore stock failed. Cannot delete order.\"}");
                        return;
                    }

                    // Sau khi khôi phục tồn kho thành công, tiến hành xóa đơn hàng
                    boolean isDeleted = orderService.deleteOrderById(orderIdInt);
                    if (!isDeleted) {
                        System.out.println("Xóa đơn hàng thất bại!");
                    } else {
                        System.out.println("Đã xóa đơn hàng do thanh toán thất bại.");
                    }
                }

                boolean updateStatusPayment = orderService.updateStatusPayment(orderIdInt, statusPayment);
                if (!updateStatusPayment) {
                    System.out.println("Cập nhật trạng thái thanh toán thất bại!");
                }
                request.setAttribute("transResult", transSuccess);
                UserService userService = new UserService();
                LogService.paymentVNPay(userService.getUserIdByOrderId(orderIdInt), String.valueOf(orderId),ipAddress);
                request.getRequestDispatcher("paymentResult.jsp").forward(request, response);
            } else {
                //RETURN PAGE ERROR
                System.out.println("GD KO HOP LE (invalid signature)");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
