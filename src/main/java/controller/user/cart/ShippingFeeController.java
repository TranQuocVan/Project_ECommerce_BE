package controller.user.cart;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "ShippingFeeController", value = "/ShippingFeeController")
public class ShippingFeeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder requestData = new StringBuilder();
        String line;

        // Đọc dữ liệu JSON từ request body
        try {
            while ((line = reader.readLine()) != null) {
                requestData.append(line);
            }

            // Chuyển đổi dữ liệu JSON thành JSONObject
            JSONObject jsonRequest = new JSONObject(requestData.toString());

            // Lấy các tham số từ JSON
            String districtId = jsonRequest.getString("to_district_id");
            String wardCode = jsonRequest.getString("to_ward_code");
            int serviceTypeId = jsonRequest.getInt("serviceTypeId");

            // Kiểm tra các tham số trước khi gửi yêu cầu
            if (districtId == null || districtId.isEmpty() || wardCode == null || wardCode.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Thiếu thông tin quận hoặc phường\"}");
                return;
            }

            // Tạo URL và Token cho API GHN
            String apiUrl = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
            String token = System.getenv("GHN_TOKEN");
            String shopId = System.getenv("SHOP_ID");

            if (token == null || shopId == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Token hoặc Shop ID không hợp lệ\"}");
                return;
            }

            // Tạo kết nối và thực hiện yêu cầu POST
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Token", token);
            connection.setRequestProperty("ShopId", shopId);
            connection.setDoOutput(true);

            // Tạo body JSON cho yêu cầu tính phí
            JSONObject requestBody = new JSONObject();
            requestBody.put("service_type_id", serviceTypeId);
            requestBody.put("from_district_id", 1443);  // Cập nhật đúng với curl
            requestBody.put("from_ward_code", "20209");
            requestBody.put("to_district_id", Integer.parseInt(districtId));
            requestBody.put("to_ward_code", wardCode);
            requestBody.put("length", 40);
            requestBody.put("width", 30);
            requestBody.put("height", 30);
            requestBody.put("weight", 1500 );
            requestBody.put("insurance_value", 0);
            requestBody.put("coupon", JSONObject.NULL);

            JSONArray items = new JSONArray();
            requestBody.put("items", items);
            // Gửi yêu cầu đến GHN
            try {
                connection.getOutputStream().write(requestBody.toString().getBytes("UTF-8"));
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Không thể gửi yêu cầu đến GHN\"}");
                return;
            }

            // Đọc dữ liệu trả về từ GHN API
            int status = connection.getResponseCode();
            InputStream inputStream;

            if (status >= 200 && status < 300) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
                System.err.println("Lỗi từ GHN API. Status code: " + status);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder responseData = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseData.append(inputLine);
            }
            in.close();

            // Xử lý phản hồi trả về từ GHN
            JSONObject jsonResponse = new JSONObject(responseData.toString());

            // DEBUG: In ra phí vận chuyển
            try {
                JSONObject dataObject = jsonResponse.getJSONObject("data");
                int totalFee = dataObject.getInt("total");
                System.out.println("✅ Tổng phí vận chuyển (total): " + totalFee + "đ");
            } catch (Exception ex) {
                System.err.println("⚠️ Không thể đọc total từ response GHN: " + ex.getMessage());
            }

            // Trả về kết quả tính phí vận chuyển cho client
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse.toString());

        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Đã xảy ra lỗi khi xử lý yêu cầu\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Đã xảy ra lỗi không xác định\"}");


        }
    }
}
