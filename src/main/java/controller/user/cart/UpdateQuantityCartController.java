package controller.user.cart;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.user.cart.ShoppingCartService;
import service.util.ReaderRequest;

import java.io.IOException;

import model.request.UpdateQuantityRequest;

@WebServlet(name = "UpdateQuantityCartController", value = "/UpdateQuantityCartController")
public class UpdateQuantityCartController extends HttpServlet {

    private final ReaderRequest readerRequest = new ReaderRequest();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        String requestBody = readerRequest.readRequestBody(request, response);

        try {
            if (readerRequest.checkRequestBodyExistence(requestBody)) {
                // Parse JSON từ request
                UpdateQuantityRequest updateRequest = gson.fromJson(requestBody, UpdateQuantityRequest.class);

                // Gọi Service để cập nhật số lượng sản phẩm trong giỏ hàng
                ShoppingCartService shoppingCartService = new ShoppingCartService();
                boolean isUpdated = shoppingCartService.updateProductToShoppingCart(
                        updateRequest.getQuantity(), updateRequest.getIdSize(),
                        user.getId(), updateRequest.isDecreaseQuantity());

                // Tạo JSON response
                JsonObject jsonResponse = new JsonObject();
                if (isUpdated) {
                    jsonResponse.addProperty("status", "ok");
                    jsonResponse.addProperty("message", "Quantity updated successfully");
                } else {
                    jsonResponse.addProperty("status", "error");
                    jsonResponse.addProperty("message", "Failed to update quantity");
                }

                response.getWriter().write(gson.toJson(jsonResponse));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("status", "error");
            errorResponse.addProperty("message", e.getMessage());
            response.getWriter().write(gson.toJson(errorResponse));
        }
    }


}
