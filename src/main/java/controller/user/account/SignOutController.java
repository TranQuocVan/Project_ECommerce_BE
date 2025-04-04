package controller.user.account;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.log.LogService;
import service.user.account.UserService;
import service.util.CookiesServices;
import service.util.SessionServices;


import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SignOutController", value = "/SignOutController")
public class SignOutController extends HttpServlet {
    private final SessionServices sessionServices = new SessionServices();
    private final UserService userService = new UserService();
    private final CookiesServices cookiesServices = new CookiesServices();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ipAddress = request.getRemoteAddr();
        // Xóa session
        HttpSession session = request.getSession(false);
        if (sessionServices.isSessionExistence(session)) {
            sessionServices.invalidateSession(session);
        }

        // Xóa cookie remember_me và cập nhật token trong cơ sở dữ liệu
        Cookie[] cookies = request.getCookies();
        if (cookiesServices.checkCookiesExistence(cookies)) {
            String token = cookiesServices.getCookie(cookies);
            try {
                UserModel user = UserService.getUserByToken(token);
                LogService.logout(user.getId(),user.getGmail(),true,ipAddress);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            
            response.addCookie(cookiesServices.clearRememberMeCookie(cookies));
        }

        // Redirect về trang đăng nhập hoặc trang chủ

        response.sendRedirect(request.getContextPath() + "/IndexController");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}