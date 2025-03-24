package controller.user.account;

import database.PasswordResetTokensDao;
import database.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.UserModel;
import service.util.GmailServices;
import util.Email;

@WebServlet(name = "ForgotPasswordController", value = "/ForgotPasswordController")
public class ForgotPasswordCController extends HttpServlet {

    private static class EmailRequest {
        String email;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        EmailRequest emailRequest = gson.fromJson(reader, EmailRequest.class);

        if (!isValidEmail(emailRequest.email)) {
            sendJsonResponse(response, false, "Email không hợp lệ!");
            return;
        }

        UserDao userDao = new UserDao();
        UserModel userModel = userDao.selectByGmail(emailRequest.email);

        if (userModel == null) {
            sendJsonResponse(response, false, "Email không tồn tại!");
            return;
        }

        String token = new GmailServices().generateTokenForgotPass();
        PasswordResetTokensDao tokenDao = new PasswordResetTokensDao();

        if (tokenDao.saveResetToken(userModel.getId(), token)) {
            boolean isSuccess = new GmailServices().sendGmailForForgotPass(emailRequest.email, token);
            sendJsonResponse(response, isSuccess, isSuccess ? "Email đã được gửi." : "Gửi email thất bại!");
        } else {
            sendJsonResponse(response, false, "Không thể tạo token đặt lại mật khẩu!");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }

    private void sendJsonResponse(HttpServletResponse response, boolean success, String message) throws IOException {
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        jsonResponse.addProperty("message", message);
        response.getWriter().print(jsonResponse);
    }


}
