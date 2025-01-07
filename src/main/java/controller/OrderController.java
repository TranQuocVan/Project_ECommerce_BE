package controller; import jakarta.servlet.*; 
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.*;
import service.OrderService;
import service.ShoppingCartItemOrderService;
import service.ShoppingCartService;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrderController", value = "/OrderController")
public class OrderController extends HttpServlet { 

@Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession();
    UserModel user = (UserModel) session.getAttribute("user");
    OrderService orderService = new OrderService();
    try{
        request.setAttribute("listOrder", orderService.getAllOrders(user.getId()));
        request.getRequestDispatcher("statusShoes.jsp").forward(request, response);
    }

    catch(Exception e){
        response.getWriter().write("{\"status\":\"false\",\"message\":\" false\"}");
    }
}
@Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession();
    UserModel user = (UserModel) session.getAttribute("user");
    OrderService orderService = new OrderService();
    ShoppingCartService shoppingCartService = new ShoppingCartService();
    ShoppingCartItemOrderService cartItemOrderService = new ShoppingCartItemOrderService();

    int paymentId = Integer.parseInt(request.getParameter("paymentId"));
    int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));

// Lấy chuỗi sizeId từ request (dữ liệu gửi qua form)
    String selectedItemsParam = request.getParameter("selectedItems");

    // Chuyển chuỗi thành danh sách Integer
    List<Integer> selectedItems = new ArrayList<>();
    if (selectedItemsParam != null && !selectedItemsParam.isEmpty()) {
        String[] selectedItemsArray = selectedItemsParam.split(",");
        for (String item : selectedItemsArray) {
            try {
                selectedItems.add(Integer.parseInt(item.trim())); // Chuyển từng phần tử thành Integer
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
    LocalDateTime now = LocalDateTime.now();
    Timestamp sqlTimestamp = Timestamp.valueOf(now);
    Order orderModel = new Order(paymentId,sqlTimestamp,request.getParameter("address"), 0, user.getId(), deliveryId);
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setGroupingSeparator('.');

    DecimalFormat df = new DecimalFormat("#,###", symbols);
//    String formattedPrice = df.format(totalPrice) + "đ";
try{
    shoppingCartService.updateStockProduct(user.getId());
    orderService.addOrder(orderModel);
    ShoppingCartItemOrders cartItemOrders = new ShoppingCartItemOrders(paymentId,0,cartItemOrderService.getOrderId(user.getId(),orderModel.getOrderDate()), selectedItems);

    cartItemOrderService.addShoppingCartItemOrders(cartItemOrders);
    shoppingCartService.cleanShoppingCartItems(user.getId());
//    request.setAttribute("totalPriceFormat", formattedPrice);
    request.setAttribute("listOrder", orderService.getAllOrders(user.getId()));
    request.getRequestDispatcher("statusShoes.jsp").forward(request, response);
}

catch(Exception e){
    e.printStackTrace(); // In ra thông tin chi tiết lỗi
    response.getWriter().write("{\"status\":\"false\",\"message\":\"Order added false\"}");
}



}
}
