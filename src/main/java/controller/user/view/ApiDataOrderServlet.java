package controller.user.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.OrderModel;
import service.user.view.ViewOrderProductsService;
import util.OrderJsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;
import java.util.Base64;

@WebServlet("/ApiDataOrder")
public class ApiDataOrderServlet extends HttpServlet {
    private static class RequestBody {
        public int id;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String orderIdParam = request.getParameter("id");
        if (orderIdParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing order ID\"}");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid order ID format\"}");
            return;
        }

        ViewOrderProductsService viewOrderProductsService = new ViewOrderProductsService();
        try {
            OrderModel orderModel = viewOrderProductsService.getSignAndPublishKeyById(orderId);

            if (orderModel == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Order not found\"}");
                return;
            }

            Map<String, String> result = new LinkedHashMap<>();
            result.put("publishKey", orderModel.getPublishKey());
            result.put("sign", orderModel.getSign());

            String json = new Gson().toJson(result);
            response.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Internal server error\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String jsonBody = sb.toString();
        Gson gson = new Gson();
        RequestBody body;
        try {
            body = gson.fromJson(jsonBody, RequestBody.class);
        } catch (com.google.gson.JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid JSON format\"}");
            return;
        }

        if (body == null || body.id <= 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing or invalid order ID\"}");
            return;
        }

        int id = body.id;
        ViewOrderProductsService viewOrderProductsService = new ViewOrderProductsService();
        try {
            OrderModel orderModel = viewOrderProductsService.getOrder(id);


            String hashBase64 = OrderJsonUtil.generateBase64HashFromOrder(orderModel);
            response.getWriter().write("{\"data\": \"" + hashBase64 + "\"}");

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error\"}");
        }
    }
}
