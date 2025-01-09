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

        // Lấy session hiện tại, không tạo mới
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp"); // Nếu không có session, chuyển về trang login
            return;
        }

        // Lấy các thông tin cần thiết từ session
        String authCodeOfSession = (String) session.getAttribute("authCode");
        String gmail = (String) session.getAttribute("gmail");
        String password = (String) session.getAttribute("password");

        if (authCodeOfSession == null || gmail == null || password == null) {
            response.sendRedirect("login.jsp"); // Nếu thông tin không đầy đủ, chuyển về trang login
            return;
        }

        // Gọi service để kiểm tra mã xác thực
        AuthenticationService authService = new AuthenticationService();
        boolean isValid = authService.validateAuthCode(authCode, authCodeOfSession);

        if (isValid) {
            try {
                // Kiểm tra Gmail đã tồn tại


                // Đăng ký người dùng nếu Gmail chưa tồn tại
                UserModel userModel = authService.registerUser(gmail, password);

                // Xử lý "Remember Me" và cập nhật thông tin session
                UserService.handleRememberMe(userModel, session, response);

                // Chuyển tiếp về trang index
                response.sendRedirect(request.getContextPath() + "/IndexController");
            } catch (SQLException e) {
                // Xử lý lỗi liên quan đến cơ sở dữ liệu
                e.printStackTrace();
                request.setAttribute("res", "Có lỗi xảy ra khi xử lý yêu cầu. Vui lòng thử lại sau.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("gmailAuthentication.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            // Trường hợp mã xác thực không đúng
            request.setAttribute("res", "Mã xác thực bạn nhập chưa đúng");
            request.setAttribute("password", password); // Giữ lại mật khẩu để người dùng không phải nhập lại
            RequestDispatcher dispatcher = request.getRequestDispatcher("gmailAuthentication.jsp");
            dispatcher.forward(request, response);
        }
    }
}