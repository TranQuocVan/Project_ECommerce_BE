package controller.user.account;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import database.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.user.account.UserService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Collections;

@WebServlet(name = "SignInGoogleController", value = "/SignInGoogleController")
public class SignInGoogleController extends HttpServlet {

    private static final String CLIENT_ID = "217805572415-osmo8h44ev71tn023pdqg192e4q7sg31.apps.googleusercontent.com";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String credential = request.getParameter("credential");

        if (credential == null || credential.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"Mã xác thực không hợp lệ!\"}");
            return;
        }

        try {
            // Xác thực mã credential với Google
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(CLIENT_ID)) // Xác minh CLIENT_ID
                    .build();

            GoogleIdToken idToken = verifier.verify(credential);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String name = (String) payload.get("name");


                UserDao userDao = new UserDao();
                UserModel userModel = userDao.selectByGmail(email);

                if (userModel == null) {
                    userModel = new UserModel();
                    userModel.setGmail(email);
                    userModel.setPassword("");
                    userModel.setRole("ROLE_USER");
                    // Facebook không cần password
                    userDao.insert(userModel); // 🚀 Lưu user vào DB
                }


                HttpSession session = request.getSession(true);
                try {
                    UserService.handleRememberMe(userModel, session, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                response.setContentType("application/json");
                response.getWriter().write("{\"success\": true, \"redirect\": \"IndexController\"}");
            } else {
                response.getWriter().write("{\"success\": false, \"message\": \"Token không hợp lệ!\"}");
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi xác thực!\"}");
        }
    }
}
