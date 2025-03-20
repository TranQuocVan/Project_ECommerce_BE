package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import model.UserModel;
import service.user.account.UserService;
import service.user.account.TokenService;

import java.io.IOException;
import java.sql.SQLException;

@WebFilter("/*") // Áp dụng cho tất cả các yêu cầu
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo nếu cần
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Chuyển đổi thành HttpServletRequest và HttpServletResponse
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Kiểm tra nếu người dùng chưa đăng nhập
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // Kiểm tra cookie remember_me
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("remember_me".equals(cookie.getName())) {
                        String token = cookie.getValue();
                        try {
                            UserModel user = UserService.getUserByToken(token);
                            if (user != null) {
                                // Tạo session mới
                                HttpSession newSession = httpRequest.getSession(true);
                                user.setPassword("******");
                                newSession.setAttribute("user", user);
                                newSession.setAttribute("isLogin", true);


                                // Tùy chọn: Cập nhật token mới để tăng cường bảo mật
                                String newToken = TokenService.generateToken();
                                user.setRememberMeToken(newToken);
                                UserService.updateRememberMeToken(user);

                                // Cập nhật cookie với token mới
                                Cookie newTokenCookie = new Cookie("remember_me", newToken);
                                newTokenCookie.setMaxAge(7 * 24 * 60 * 60);
                                newTokenCookie.setHttpOnly(true);
                                newTokenCookie.setSecure(true);
                                newTokenCookie.setPath("/");
                                httpResponse.addCookie(newTokenCookie);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }

        // Tiếp tục xử lý yêu cầu
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Hủy nếu cần
    }
}
