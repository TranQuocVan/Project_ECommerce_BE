package controller.user.cart;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import database.ShoppingCartItemsDao;
import service.user.account.UserService;
import service.util.ReaderRequest;

import java.io.IOException;

import model.request.StockRequest;

@WebServlet(name = "CheckStockController", value = "/CheckStockController")
public class CheckStockController extends HttpServlet {

    private final UserService userService = new UserService();
    private final ReaderRequest readerRequest = new ReaderRequest();
    private final ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Get user session
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        // Check if user is logged in
        if (!userService.isUserModelExistence(user)) {
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("status", "error");
            errorResponse.addProperty("message", "User is not logged in");
            response.getWriter().write(gson.toJson(errorResponse));
            return;
        }

        String requestBody = readerRequest.readRequestBody(request, response);

        try {
            if (readerRequest.checkRequestBodyExistence(requestBody)) {
                // Parse JSON input using Gson
                StockRequest stockRequest = gson.fromJson(requestBody, StockRequest.class);

                boolean isStockAvailable = shoppingCartItemsDao.checkStockProduct(
                        stockRequest.getIdSize(), user.getId(), stockRequest.getQuantity());

                // Create response JSON
                JsonObject jsonResponse = new JsonObject();
                if (isStockAvailable) {
                    jsonResponse.addProperty("status", "ok");
                    jsonResponse.addProperty("message", "Stock is available");
                } else {
                    jsonResponse.addProperty("status", "error");
                    jsonResponse.addProperty("message", "Insufficient stock");
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
