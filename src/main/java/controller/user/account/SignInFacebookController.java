package controller.user.account;

import database.LogDAO;
import database.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.log.LogService;
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


        UserDao userDao = new UserDao();
        UserModel userModel = userDao.selectByGmail(email);

        if (userModel != null) {
            if (userModel.getFacebook_id() == 0 ) {
                try {
                    userDao.updateTokenFb(facebook_id,email);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                userModel.setFacebook_id(facebook_id);
            }
        } else {
            userModel = userDao.selectByFacebookId(facebook_id);

            if (userModel == null) {
                userModel = new UserModel();
                userModel.setGmail(email);
                userModel.setPassword("");
                userModel.setRole("ROLE_USER");
                userModel.setFacebook_id(facebook_id);
                userDao.insert(userModel); // 🚀 Lưu user vào DB
            }
        }

        HttpSession session = request.getSession(true);
        try {
            UserService.handleRememberMe(userModel, session, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
