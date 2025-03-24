package controller.user.cart;

<<<<<<< HEAD
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

=======
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import model.UserModel;
import org.json.JSONObject;
import service.user.account.UserService;
import service.user.order.OrderService;
import service.user.cart.ShoppingCartService;
import service.user.product.ProductService;
import service.util.ReaderRequest;

import java.io.BufferedReader;
import java.io.IOException;
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb
@WebServlet(name = "AddToCartController", value = "/AddToCartController")
public class AddToCartController extends HttpServlet {

    private final ShoppingCartService shoppingCartService = new ShoppingCartService();
<<<<<<< HEAD
    private final UserService userService = new UserService();
    private final ReaderRequest readerRequest = new ReaderRequest();
    private final Gson gson = new Gson();
=======
    private final OrderService orderService = new OrderService();
    private final UserService userService = new UserService();
    private final ReaderRequest readerRequest = new ReaderRequest();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        if (!userService.isUserModelExistence(user)) {
<<<<<<< HEAD
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
=======
            response.getWriter().write("{\"status\":\"error\",\"message\":\"User not logged in\"}");
            return;
        }

//        StringBuilder sb = new StringBuilder();
//        try (BufferedReader reader = request.getReader()) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//        }

        String requestBody = readerRequest.readRequestBody(request,response);

        try {
            if (readerRequest.checkRequestBodyExistence(requestBody)) {
                // Parse JSON from the request
                JSONObject productJson = new JSONObject(requestBody);
                int sizeId = productJson.getInt("idSize");
                int quantity = productJson.getInt("quantity"); // Extract quantity from the JSON

                // Add product to shopping cart with the provided quantity
                boolean isAdded = shoppingCartService.addProductToShoppingCart(quantity, sizeId, user.getId());

                if (isAdded) {
                    response.getWriter().write("{\"status\":\"ok\",\"message\":\"Product added successfully\"}");
                } else {
                    response.getWriter().write("{\"status\":\"error\",\"message\":\"Failed to add product to cart\"}");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb
