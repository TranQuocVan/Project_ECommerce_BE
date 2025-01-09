package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import org.json.JSONObject;
import service.ShoppingCartService;
import database.ShoppingCartItemsDao;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "CheckStockController", value = "/CheckStockController")
public class CheckStockController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Get user session
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        // Check if user is logged in
        if (user == null) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"User is not logged in\"}");
            return;
        }

        // Read JSON data from the request
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException e) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Failed to read request body\"}");
            return;
        }

        try {
            // Parse JSON input
            JSONObject productJson = new JSONObject(requestBody.toString());
            int sizeId = productJson.getInt("idSize");
            int quantity = productJson.optInt("quantity", 1); // Default to 1 if not provided

            // Check stock using DAO
            ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
            boolean isStockAvailable = shoppingCartItemsDao.checkStockProduct(sizeId, user.getId(), quantity);

            // Respond based on stock availability
            if (isStockAvailable) {
                response.getWriter().write("{\"status\":\"ok\",\"message\":\"Stock is available\"}");
            } else {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"Insufficient stock\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
