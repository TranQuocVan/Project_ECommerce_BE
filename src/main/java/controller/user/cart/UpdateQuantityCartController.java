package controller.user.cart;

<<<<<<< HEAD
import com.google.gson.Gson;
import com.google.gson.JsonObject;
=======
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
<<<<<<< HEAD
import service.user.cart.ShoppingCartService;
import service.util.ReaderRequest;

import java.io.IOException;

import model.request.UpdateQuantityRequest;

=======
import org.json.JSONObject;
import service.user.cart.ShoppingCartService;
import service.util.ReaderRequest;

import java.io.BufferedReader;
import java.io.IOException;

>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb
@WebServlet(name = "UpdateQuantityCartController", value = "/UpdateQuantityCartController")
public class UpdateQuantityCartController extends HttpServlet {

    private final ReaderRequest readerRequest = new ReaderRequest();
<<<<<<< HEAD
    private final Gson gson = new Gson();
=======
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
<<<<<<< HEAD

        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        String requestBody = readerRequest.readRequestBody(request, response);
=======
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

//        StringBuilder sb = new StringBuilder();
//        try (BufferedReader reader = request.getReader()) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//        }

        String requestBody = readerRequest.readRequestBody(request,response);
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb

        try {
            if (readerRequest.checkRequestBodyExistence(requestBody)) {
                // Parse JSON từ request
<<<<<<< HEAD
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


=======
                JSONObject productJson = new JSONObject(requestBody);

                int sizeId = productJson.getInt("idSize"); // Nhận giá trị sizeId
                int quantity = productJson.getInt("quantity"); // Nhận giá trị quantity

                boolean isDecreaseQuantity = productJson.optBoolean("isDecreaseQuantity", false);

                // Gọi Service để lưu sản phẩm
                ShoppingCartService shoppingCartService = new ShoppingCartService();

                // Thêm sản phẩm vào giỏ hàng
                boolean isUpdated = shoppingCartService.updateProductToShoppingCart(quantity, sizeId, user.getId(), isDecreaseQuantity);
                if (isUpdated) {
                    response.getWriter().write("{\"status\":\"ok\",\"message\":\"Quantity updated successfully\"}");
                } else {
                    response.getWriter().write("{\"status\":\"error\",\"message\":\"Failed to update quantity\"}");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }


    }
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb
}
