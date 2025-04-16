package controller.user.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "DistrictController", value = "/DistrictController")
public class DistrictController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Lấy provinceID từ yêu cầu
        String provinceID = request.getParameter("provinceID");
        if (provinceID == null || provinceID.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"provinceID is required\"}");
            return;
        }

        // Cấu hình URL và token
        String apiUrl = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=" + provinceID;
        String token = System.getenv("GHN_TOKEN");

        // Tạo kết nối và thực hiện yêu cầu GET
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Token", token);

        // Đọc dữ liệu trả về từ API
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseData = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            responseData.append(inputLine);
        }
        in.close();

        // Đảm bảo trả về dữ liệu JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseData.toString());
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
