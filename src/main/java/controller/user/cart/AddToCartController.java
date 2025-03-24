package controller.user.cart;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.user.account.UserService;
import service.user.cart.ShoppingCartService;
import service.util.ReaderRequest;
import model.request.ProductRequest;

import java.io.IOException;

@WebServlet(name = "AddToCartController", value = "/AddToCartController")
public class AddToCartController extends HttpServlet {

    private final ShoppingCartService shoppingCartService = new ShoppingCartService();
    private final UserService userService = new UserService();
    private final ReaderRequest readerRequest = new ReaderRequest();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        if (!userService.isUserModelExistence(user)) {
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("status", "error");
            errorResponse.addProperty("message", "User not logged in");
            response.getWriter().write(gson.toJson(errorResponse));
            return;
        }

        String requestBody = readerRequest.readRequestBody(request, response);

        try {
            if (readerRequest.checkRequestBodyExistence(requestBody)) {
                // Chuyển JSON thành Java Object
                ProductRequest productRequest = gson.fromJson(requestBody, ProductRequest.class);

                // Gọi service để thêm sản phẩm vào giỏ hàng
                boolean isAdded = shoppingCartService.addProductToShoppingCart(
                        productRequest.getQuantity(), productRequest.getIdSize(), user.getId());

                // Chuẩn bị phản hồi JSON
                JsonObject jsonResponse = new JsonObject();
                if (isAdded) {
                    jsonResponse.addProperty("status", "ok");
                    jsonResponse.addProperty("message", "Product added successfully");
                } else {
                    jsonResponse.addProperty("status", "error");
                    jsonResponse.addProperty("message", "Failed to add product to cart");
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
