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
import model.UserModel;

import java.io.IOException;

@WebFilter("/admin/*") // This protects all resources under /admin/*
public class AdminRoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Example: Check the user's role stored in the session
        UserModel userModel = (UserModel) httpRequest.getSession().getAttribute("user");
        if(userModel != null ) {

        if (userModel.getRole()!= null && userModel.getRole().equals("admin")) {
            // User is an admin, continue processing the request
            chain.doFilter(request, response);
        } else {
            // User is not an admin, redirect to an error page or login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/errorPages/403.jsp");
        }
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}
