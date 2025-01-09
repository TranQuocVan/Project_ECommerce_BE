package controller;

import database.GroupProductDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.GroupProductModel;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexController", value = "/IndexController")
public class IndexController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// Tạo đối tượng DAO để truy xuất dữ liệu
        GroupProductDao groupProductDao = new GroupProductDao();

        // Lấy danh sách tất cả GroupProducts
        List<GroupProductModel> groupProducts = groupProductDao.getAllGroupProducts();

        // Đặt danh sách vào request attribute để truyền cho JSP
        request.setAttribute("groupProducts", groupProducts);

        // Chuyển tiếp (forward) request đến index.jsp
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}