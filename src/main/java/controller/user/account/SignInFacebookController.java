package controller.user.account;

import database.LogDAO;
import database.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.log.LogService;
import service.user.account.AuthenticationService;
import service.user.account.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "SignInFacebookController", value = "/SignInFacebookController")
public class SignInFacebookController extends HttpServlet {
    private final UserService userService = new UserService();
    private final AuthenticationService authenticationService = new AuthenticationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Đọc dữ liệu JSON từ request
        BufferedReader reader = request.getReader();
        JsonObject jsonRequest = JsonParser.parseReader(reader).getAsJsonObject();
        reader.close();

        // Lấy dữ liệu từ JSON
        String email = jsonRequest.get("email").getAsString();
        long facebook_id = Long.parseLong(jsonRequest.get("id").getAsString());
        String ipAddress = request.getRemoteAddr();
        Map<String, String> result = new HashMap<>();
//
//        // 🛡️ Xác thực accessToken với Facebook
//        if (!verifyFacebookAccessToken(accessToken, email)) {
//            result.put("status", "error");
//            result.put("message", "Invalid Facebook access token");
//            response.getWriter().write(new Gson().toJson(result));
//            return;
//        }
        HttpSession session = request.getSession(true);

        UserModel userModel = null;
        try {
            userModel = authenticationService.registerUser(email, "6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            UserService.handleRememberMe(userModel, session, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        LogService.signUp(userModel.getId(),email,true,ipAddress);





//        LogDAO.insertLog(userModel.getId(), "LOGIN_SUCCESS", "users", email, "FACEBOOK_LOGIN", ipAddress);
        LogService.logLoginWithFB(userModel.getId(),String.valueOf(facebook_id),true,ipAddress);
        result.put("status", "success");
        result.put("redirect", request.getContextPath() + "/IndexController");

        response.getWriter().write(new Gson().toJson(result));
    }


//    private boolean verifyFacebookAccessToken(String accessToken, String email) {
//        try {
//            String appAccessToken = "1143031257503719|2c5aaebdd2490f8385eb23196e2c166a";
//            String url = "https://graph.facebook.com/debug_token?input_token=" + accessToken + "&access_token=" + appAccessToken;
//            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//            connection.setRequestMethod("GET");
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuilder response = new StringBuilder();
//            String responseLine;
//
//            while ((responseLine = reader.readLine()) != null) {
//                response.append(responseLine);
//            }
//            reader.close();
//
//            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
//            JsonObject data = jsonResponse.getAsJsonObject("data");
//
//            return data.get("is_valid").getAsBoolean();
//        } catch (Exception e) {
//            return false;
//        }
//    }
}
