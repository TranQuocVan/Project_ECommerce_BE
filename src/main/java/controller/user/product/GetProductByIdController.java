package controller.user.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.ProductModel;
import service.user.product.ProductService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "GetProductByIdController", value = "/GetProductByIdController")
public class GetProductByIdController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int id = 1;  // Giá trị mặc định nếu không thể chuyển đổi
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            // Xử lý lỗi nếu không thể chuyển đổi (người dùng nhập vào giá trị không phải số)
            System.out.println("Giá trị id không hợp lệ");
        }
        ProductService service = new ProductService();
        try {
            ProductModel product = service.getProductById(id);
            request.setAttribute("product", product);
            request.getRequestDispatcher("productDetails.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}