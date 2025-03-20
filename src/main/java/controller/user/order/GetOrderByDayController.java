package controller.user.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.OrderModel;
import service.user.order.OrderService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "GetOrderByDateController", value = "/GetOrderByDateController")
public class GetOrderByDayController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dateParam = request.getParameter("date");
        if (dateParam == null || dateParam.isEmpty()) {
            request.setAttribute("error", "Date parameter is required.");
            request.getRequestDispatcher("orderByDay.jsp").forward(request, response);
            return;
        }

        OrderService service = new OrderService();
        List<OrderModel> orders = service.getOrderByDate(Timestamp.valueOf(dateParam));
        if (orders != null && !orders.isEmpty()) {
            request.setAttribute("orders", orders);
        } else {
            request.setAttribute("error", "No orders found for the specified date.");
        }
        request.getRequestDispatcher("orderByDay.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
