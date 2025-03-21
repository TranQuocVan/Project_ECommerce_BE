package controller.user.cart;

import database.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import org.json.JSONObject;
import database.ShoppingCartItemsDao;
import service.user.account.UserService;
import service.util.ReaderRequest;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "CheckStockController", value = "/CheckStockController")
public class CheckStockController extends HttpServlet {

    private final UserService userService = new UserService();
    private final ReaderRequest readerRequest = new ReaderRequest();
    private final ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Get user session
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        // Check if user is logged in
        if (!userService.checkUserModelExistence(user)) {
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
}
