package controller.user.cart;

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
@WebServlet(name = "AddToCartController", value = "/AddToCartController")
public class AddToCartController extends HttpServlet {

    private final ShoppingCartService shoppingCartService = new ShoppingCartService();
    private final OrderService orderService = new OrderService();
    private final UserService userService = new UserService();
    private final ReaderRequest readerRequest = new ReaderRequest();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        if (!userService.isUserModelExistence(user)) {
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