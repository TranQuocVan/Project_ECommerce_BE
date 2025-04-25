package controller.admin.sign;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import model.OrderModel;
import service.user.view.ViewOrderProductsService;
import util.OrderJsonUtil;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SignAdminController", value = "/SignAdminController")
public class SignAdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Có thể triển khai nếu cần xử lý GET
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Lấy danh sách tất cả đơn hàng từ cơ sở dữ liệu
            ViewOrderProductsService service = new ViewOrderProductsService();
            List<OrderModel> orders = service.getAllOrders();

            // Tạo JSON chứa danh sách trạng thái chữ ký
            String signStatusJson = OrderJsonUtil.generateOrderSignStatusJson(orders);

            // Gửi phản hồi
            response.getWriter().write(signStatusJson);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Unexpected error occurred\"}");
        }
    }
}