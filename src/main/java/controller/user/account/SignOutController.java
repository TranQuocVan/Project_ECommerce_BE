package controller.user.account;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
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
        // Xóa session
        HttpSession session = request.getSession(false);
        if (sessionServices.isSessionExistence(session)) {
            sessionServices.invalidateSession(session);
        }

        // Xóa cookie remember_me và cập nhật token trong cơ sở dữ liệu
        Cookie[] cookies = request.getCookies();
        if (cookiesServices.checkCookiesExistence(cookies)) {
//            for (Cookie cookie : cookies) {
//                if ("remember_me".equals(cookie.getName())) {
//                    String token = cookie.getValue();
//                    try {
//                        userService.invalidateToken(token);
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//
//                    // Xóa cookie trên trình duyệt
//                    cookie.setValue("");
//                    cookie.setPath("/");
//                    cookie.setMaxAge(0);
//                    response.addCookie(cookie);
//                }
//            }
            response.addCookie(cookiesServices.clearRememberMeCookie(cookies));
        }

        // Redirect về trang đăng nhập hoặc trang chủ

        response.sendRedirect(request.getContextPath() + "/IndexController");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}