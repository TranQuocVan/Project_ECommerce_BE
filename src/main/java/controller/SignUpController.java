package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.TokenService;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "SignUpController", value = "/SignUpController")
public class SignUpController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Có thể redirect đến trang đăng nhập hoặc đăng ký
        response.sendRedirect("signup.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gmail = request.getParameter("gmail");
        String password = request.getParameter("password");

        UserModel userModel = new UserModel(gmail, password);

        try {
            userModel = UserService.checkValidGmailAndPassword(userModel);
            if (userModel == null) {
                request.setAttribute("res", "This gmail or password is incorrect");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
            } else {
                // Tạo session
                HttpSession session = request.getSession(true);
                userModel.setPassword("******"); // Không lưu mật khẩu thực vào session
                session.setAttribute("user", userModel);

                // Tạo token và lưu vào cơ sở dữ liệu
                String token = TokenService.generateToken();
                userModel.setRememberMeToken(token);
                UserService.updateRememberMeToken(userModel);

                // Tạo cookie lưu token
                Cookie tokenCookie = new Cookie("remember_me", token);
                tokenCookie.setMaxAge(7 * 24 * 60 * 60); // Cookie tồn tại 7 ngày
                tokenCookie.setHttpOnly(true);
                tokenCookie.setSecure(true); // Đảm bảo chỉ gửi qua HTTPS
                tokenCookie.setPath("/"); // Có hiệu lực trên toàn bộ ứng dụng
                response.addCookie(tokenCookie);

                session.setAttribute("isLogin", true);

                response.sendRedirect("index.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}