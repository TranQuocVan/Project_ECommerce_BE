package controller.user.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import org.json.JSONObject;
import service.user.cart.ShoppingCartService;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "UpdateQuantityCartController", value = "/UpdateQuantityCartController")
public class UpdateQuantityCartController extends HttpServlet {

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

            int sizeId = productJson.getInt("idSize"); // Nhận giá trị sizeId
            int quantity = productJson.getInt("quantity"); // Nhận giá trị quantity

            boolean isDecreaseQuantity = productJson.optBoolean("isDecreaseQuantity", false);

            // Gọi Service để lưu sản phẩm
            ShoppingCartService shoppingCartService = new ShoppingCartService();

            // Thêm sản phẩm vào giỏ hàng
            boolean isUpdated = shoppingCartService.updateProductToShoppingCart(quantity, sizeId, user.getId(),isDecreaseQuantity);
            if (isUpdated) {
                response.getWriter().write("{\"status\":\"ok\",\"message\":\"Quantity updated successfully\"}");
            } else {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"Failed to update quantity\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }


    }
}
