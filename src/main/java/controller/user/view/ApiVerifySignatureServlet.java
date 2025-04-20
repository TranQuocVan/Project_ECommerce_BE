package controller.user.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderModel;
import service.user.view.ViewOrderProductsService;
import util.OrderJsonUtil;
import util.SignatureVerifierUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/ApiVerifySignature")
public class ApiVerifySignatureServlet extends HttpServlet {
    private static class RequestBody {
        public int orderId;
        public String base64Signature;
        public String base64PublicKey;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Read JSON body
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        // Parse JSON
        Gson gson = new Gson();
        RequestBody body;
        try {
            body = gson.fromJson(sb.toString(), RequestBody.class);
        } catch (JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid JSON format\"}");
            return;
        }

        // Validate input
        if (body == null || body.orderId <= 0 || body.base64Signature == null || body.base64PublicKey == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing required fields: orderId, base64Signature, or base64PublicKey\"}");
            return;
        }

        // Fetch order and generate content
        ViewOrderProductsService service = new ViewOrderProductsService();
        try {
            OrderModel orderModel = service.getOrder(body.orderId);
            if (orderModel == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Order not found\"}");
                return;
            }

            String hashBase64 = OrderJsonUtil.generateBase64HashFromOrder(orderModel);
            boolean isValid = SignatureVerifierUtil.verifySignature(hashBase64, body.base64Signature, body.base64PublicKey);

            // Save signature if valid
            if (isValid) {
                service.updateOrderSignature(body.orderId, body.base64Signature, body.base64PublicKey);
            }

            // Send response
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("isValid", isValid);
            response.getWriter().write(gson.toJson(result));
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error\"}");
        }
    }
}