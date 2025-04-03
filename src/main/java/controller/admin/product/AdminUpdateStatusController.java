package controller.admin.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.admin.order.OrderAdminService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AdminUpdateStatusController", value = "/AdminUpdateStatusController")
public class AdminUpdateStatusController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int status =  Integer.parseInt(request.getParameter("nameStatus"));
        int orderId = Integer.parseInt(request.getParameter("orderId"));

//        OrderAdminService orderAdminService = new OrderAdminService();
//        try {
//            if (orderAdminService.insertStatus(status,orderId)){
//                response.sendRedirect("OrderAdminController");
//            }
//            else {
//                response.sendRedirect("OrderAdminController");
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


    }
}
