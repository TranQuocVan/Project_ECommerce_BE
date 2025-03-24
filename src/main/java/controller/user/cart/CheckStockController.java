package controller.user.cart;

<<<<<<< HEAD
import com.google.gson.Gson;
import com.google.gson.JsonObject;
=======
import database.UserDao;
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
<<<<<<< HEAD
=======
import org.json.JSONObject;
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb
import database.ShoppingCartItemsDao;
import service.user.account.UserService;
import service.util.ReaderRequest;

<<<<<<< HEAD
import java.io.IOException;

import model.request.StockRequest;

=======
import java.io.BufferedReader;
import java.io.IOException;

>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb
@WebServlet(name = "CheckStockController", value = "/CheckStockController")
public class CheckStockController extends HttpServlet {

    private final UserService userService = new UserService();
    private final ReaderRequest readerRequest = new ReaderRequest();
    private final ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
<<<<<<< HEAD
    private final Gson gson = new Gson();

=======
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Get user session
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        // Check if user is logged in
        if (!userService.isUserModelExistence(user)) {
<<<<<<< HEAD
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


=======
            response.getWriter().write("{\"status\":\"error\",\"message\":\"User is not logged in\"}");
            return;
        }

//        // Read JSON data from the request
//        StringBuilder requestBody = new StringBuilder();
//        try (BufferedReader reader = request.getReader()) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                requestBody.append(line);
//            }
//        } catch (IOException e) {
//            response.getWriter().write("{\"status\":\"error\",\"message\":\"Failed to read request body\"}");
//            return;
//        }
        String requestBody = readerRequest.readRequestBody(request,response);

        try {
            if (readerRequest.checkRequestBodyExistence(requestBody)) {
                // Parse JSON input
                JSONObject productJson = new JSONObject(requestBody.toString());
                int sizeId = productJson.getInt("idSize");
                int quantity = productJson.optInt("quantity", 1); // Default to 1 if not provided

                boolean isStockAvailable = shoppingCartItemsDao.checkStockProduct(sizeId, user.getId(), quantity);

                // Respond based on stock availability
                if (isStockAvailable) {
                    response.getWriter().write("{\"status\":\"ok\",\"message\":\"Stock is available\"}");
                } else {
                    response.getWriter().write("{\"status\":\"error\",\"message\":\"Insufficient stock\"}");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb
}
