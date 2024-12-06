package controller;

import database.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.AuthenticationService;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "GmailAuthenticationController", value = "/GmailAuthenticationController")
public class GmailAuthenticationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String authCode = request.getParameter("authCode");

        HttpSession session = request.getSession(false); // Lấy session hiện tại, không tạo mới
        if (session == null) {
            response.sendRedirect("login.jsp"); // Nếu không có session, chuyển về trang login
            return;
        }

        String authCodeOfSession = (String) session.getAttribute("authCode");
        String gmail = (String) session.getAttribute("gmail");
        String password = (String) session.getAttribute("password");

        // Gọi Service để xử lý logic nghiệp vụ
        AuthenticationService authService = new AuthenticationService();
        boolean isValid = authService.validateAuthCode(authCode, authCodeOfSession);

        if (isValid) {
            UserModel userModel = authService.registerUser(gmail, password);

            // Cập nhật thông tin session
//            session.setAttribute("user", userModel);
//            session.setAttribute("isLogin", true);

            // Tạo session và xử lý "Remember Me"
            try {
                UserService.handleRememberMe(userModel, session, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Chuyển tiếp về trang index
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        } else {
            // Trường hợp mã xác thực không đúng
            request.setAttribute("res", "Mã xác thực bạn nhập chưa đúng");
            request.setAttribute("password", password);
            RequestDispatcher dispatcher = request.getRequestDispatcher("gmailAuthentication.jsp");
            dispatcher.forward(request, response);
        }
    }
}