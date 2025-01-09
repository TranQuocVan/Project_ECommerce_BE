package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.UserService;


import javax.mail.Session;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SignOutController", value = "/SignOutController")
public class SignOutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");
        // Xóa session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Xóa cookie remember_me và cập nhật token trong cơ sở dữ liệu
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("remember_me".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    try {
                        UserService.invalidateToken(token);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // Xóa cookie trên trình duyệt
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        // Redirect về trang đăng nhập hoặc trang chủ

        response.sendRedirect(request.getContextPath() + "/IndexController");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}