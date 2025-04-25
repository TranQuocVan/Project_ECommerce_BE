package controller.user.sign;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.request.UserPublicKeyModel;
import service.user.key.UserPublicKeyService;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddKeyController", value = "/AddKeyController")
public class AddKeyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý cho GET, chỉ cần POST
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Đọc JSON từ request body
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String json = sb.toString();
        if (json.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Empty request body\"}");
            return;
        }

        Gson gson = new Gson();
        UserPublicKeyModel body;
        try {
            body = gson.fromJson(json, UserPublicKeyModel.class);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid JSON format: " + e.getMessage() + "\"}");
            return;
        }

        // Kiểm tra body và các trường
        if (body == null || body.getGmail() == null || body.getGmail().trim().isEmpty() ||
                body.getPublicKey() == null || body.getPublicKey().trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing or invalid email or public key\"}");
            return;
        }

        // Gọi dịch vụ để chèn public key vào cơ sở dữ liệu
        UserPublicKeyService keyService = new UserPublicKeyService();
        try {
            boolean success = keyService.insertUserPublicKeyByGmail(body.getGmail(), body.getPublicKey());
            Map<String, Object> result = new HashMap<>();
            result.put("success", success);
            response.getWriter().write(gson.toJson(result));
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Internal server error\"}");
        }
    }
}