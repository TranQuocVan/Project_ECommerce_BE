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

        if (user == null) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"User not logged in\"}");
            return;
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String jsonData = sb.toString();

        try {
            // Parse JSON from the request
            JSONObject productJson = new JSONObject(jsonData);
            int sizeId = productJson.getInt("idSize");
            int quantity = productJson.getInt("quantity"); // Extract quantity from the JSON

            // Call services
            OrderService orderService = new OrderService();
            ShoppingCartService shoppingCartService = new ShoppingCartService();

            // Add product to shopping cart with the provided quantity
            boolean isAdded = shoppingCartService.addProductToShoppingCart(quantity, sizeId, user.getId());

            if (isAdded) {
                response.getWriter().write("{\"status\":\"ok\",\"message\":\"Product added successfully\"}");
            } else {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"Failed to add product to cart\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}