package controller.user.account;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.log.LogService;
import service.user.account.AuthenticationService;
import service.user.account.UserService;
import service.util.SessionServices;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "GmailAuthenticationController", value = "/GmailAuthenticationController")
public class GmailAuthenticationController extends HttpServlet {
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final SessionServices sessionServices = new SessionServices();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String authCode = request.getParameter("authCode");
        String ipAddress = request.getRemoteAddr();

        // Lấy session hiện tại (không tạo mới nếu chưa có)
        HttpSession session = request.getSession(false);

        // Lấy mã xác thực từ session
        String sessionAuthCode = (String) session.getAttribute("authCode");

        if (sessionAuthCode == null || !authenticationService.validateAuthCode(authCode, sessionAuthCode)) {
            request.setAttribute("res", "Mã xác thực không đúng. Vui lòng thử lại.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/gmailAuthentication.jsp");
            dispatcher.forward(request, response);
            return;
        }
        try {
            // Đăng ký người dùng nếu Gmail chưa tồn tại
            String gmail = (String) session.getAttribute("gmail");
            String password = (String) session.getAttribute("password");
            UserModel userModel = authenticationService.registerUser(gmail, password);

            // Xử lý "Remember Me" và cập nhật session
            UserService.handleRememberMe(userModel, session, response);

            LogService.signUp(userModel.getId(),gmail,true,ipAddress);

            // Chuyển tiếp về trang chính sau khi đăng nhập thành công
            response.sendRedirect(request.getContextPath() + "/IndexController");
        } catch (SQLException e) {
            LogService.signUp(0,(String) session.getAttribute("gmail"),false,ipAddress);
            e.printStackTrace(); // Có thể thay bằng logging
            request.setAttribute("res", "Có lỗi xảy ra khi xử lý yêu cầu. Vui lòng thử lại sau.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/gmailAuthentication.jsp");
            dispatcher.forward(request, response);
        }
    }
}
