package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import org.json.JSONObject;
import service.OrderService;
import service.ShoppingCartService;


import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "AddToCartController", value = "/AddToCartController")
public class AddToCartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        try {
            // Parse JSON từ request
            String jsonData = sb.toString();
            JSONObject productJson = new JSONObject(jsonData);

            int sizeId = productJson.getInt("idSize");

            // Gọi Service để lưu sản phẩm
            OrderService orderService = new OrderService();
            ShoppingCartService shoppingCartService = new ShoppingCartService();

            // Thêm sản phẩm vào giỏ hàng
            shoppingCartService.addProductToShoppingCart(1, sizeId, user.getId());

            // Trả về phản hồi thành công
            response.getWriter().write("{\"status\":\"success\",\"message\":\"Product added successfully\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}


