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
import service.log.LogService;
import service.user.account.AuthenticationService;
import service.user.account.UserService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Collections;

@WebServlet(name = "SignInGoogleController", value = "/SignInGoogleController")
public class SignInGoogleController extends HttpServlet {

    private static final String CLIENT_ID = "217805572415-osmo8h44ev71tn023pdqg192e4q7sg31.apps.googleusercontent.com";
    private final AuthenticationService authenticationService = new AuthenticationService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String credential = request.getParameter("credential");
        String ipAddress = request.getRemoteAddr();

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


                LogService.logLoginWithGG(userModel.getId(), credential,true,ipAddress);

                response.setContentType("application/json");
                response.getWriter().write("{\"success\": true, \"redirect\": \"IndexController\"}");

            } else {
                LogService.logLoginWithGG(0,credential,true,ipAddress);
                response.getWriter().write("{\"success\": false, \"message\": \"Token không hợp lệ!\"}");
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi xác thực!\"}");
        }
    }
}
