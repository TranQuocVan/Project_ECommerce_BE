package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "SignInController", value = "/SignInController")
public class SignInController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Có thể redirect đến trang đăng nhập hoặc đăng ký
        response.sendRedirect("signIn.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ request
        String gmail = request.getParameter("gmail");
        String password = request.getParameter("password");

        // Gọi UserService để xử lý
        try {
            // Kiểm tra thông tin tài khoản qua service
            UserModel userModel = UserService.authenticateUser(gmail, password);

            if (userModel == null) {
                // Trường hợp thông tin không hợp lệ
                request.setAttribute("res", "This gmail or password is incorrect");
                request.getRequestDispatcher("signIn.jsp").forward(request, response);
                return;
            }

            // Tạo session và xử lý "Remember Me"
            HttpSession session = request.getSession(true);
            UserService.handleRememberMe(userModel, session, response);

            // Chuyển hướng về trang index
            response.sendRedirect("index.jsp");
        } catch (SQLException e) {
            // Xử lý ngoại lệ từ cơ sở dữ liệu
            throw new RuntimeException("Database error occurred: " + e.getMessage(), e);
        }
    }

}