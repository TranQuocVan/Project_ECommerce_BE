package controller; import jakarta.servlet.*; 
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.OrderModel;
import model.UserModel;
import service.OrderService;

import java.io.IOException;
@WebServlet(name = "OrderController", value = "/OrderController") 
public class OrderController extends HttpServlet { 

@Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { } 
@Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession();
    UserModel user = (UserModel) session.getAttribute("user");
    OrderService orderService = new OrderService();

    int paymentId = Integer.parseInt(request.getParameter("paymentId"));
    int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
    float totalPrice = Float.parseFloat(request.getParameter("totalPrice"));

    OrderModel orderModel =
            new OrderModel(paymentId,null,request.getParameter("address"),
                    totalPrice, user.getId(), deliveryId);
try{
    orderService.addOrder(orderModel);
    response.getWriter().write("{\"status\":\"success\",\"message\":\"Order added successfully\"}");
}
catch(Exception e){
    response.getWriter().write("{\"status\":\"false\",\"message\":\"Order added false\"}");

}



}
}
