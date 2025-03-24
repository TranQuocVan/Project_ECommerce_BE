package controller.user.account;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.user.account.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ResetPasswordForgotController", value = "/ResetPasswordForgotController")
public class ResetPasswordForgotController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Đọc dữ liệu từ request
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        JsonObject requestData = gson.fromJson(reader, JsonObject.class);

        String token = requestData.get("token").getAsString();
        String newPassword = requestData.get("password").getAsString();

        UserService userService = new UserService();
        JsonObject jsonResponse = userService.ResetPasswordForgot(token, newPassword);

        // Nếu đặt lại mật khẩu thành công, thêm URL chuyển hướng vào JSON response
        if (jsonResponse.get("success").getAsBoolean()) {
            jsonResponse.addProperty("redirect", request.getContextPath() + "/signIn.jsp");
        }

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(jsonResponse));
        out.flush();
    }
}
