package filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;

import java.io.IOException;

@WebFilter("/Login/*") // This protects all resources under /admin/*
public class Login implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Chuyển đổi thành HttpServletRequest và HttpServletResponse
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Kiểm tra session
        HttpSession session = httpRequest.getSession(false); // Không tạo mới session
        boolean isLogin = false;

        if (session != null) {
            Object loginStatus = session.getAttribute("isLogin");
            if (loginStatus instanceof Boolean) {
                isLogin = (Boolean) loginStatus;
            }
        }

        // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
        if (!isLogin) {
            httpResponse.sendRedirect("../signin.jsp"); // Chuyển đến trang login
            return; // Dừng xử lý request
        }

        // Tiếp tục xử lý request nếu đã đăng nhập
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Hủy bỏ tài nguyên nếu cần
    }
}
