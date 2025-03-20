package controller.user.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.OrderModel;
import service.user.order.OrderService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "FindByIdController", value = "/FindByIdController")
public class FindByIdController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int id = 1;  // Giá trị mặc định nếu không thể chuyển đổi
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            System.out.println("Giá trị id không hợp lệ");// chuyển đôi giá trị
            request.setAttribute("error", "Invalid order ID format.");
        }
//123456
        OrderService service = new OrderService();
        List<OrderModel> order = service.findByIdOrder(id);
        if (order != null) {
            request.setAttribute("order", order);
        } else {
            request.setAttribute("error", "Order not found.");
        }
        request.getRequestDispatcher("orderDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
