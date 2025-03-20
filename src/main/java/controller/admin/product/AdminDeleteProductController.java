package controller.admin.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.ProductModel;
import service.admin.product.ProductAdminService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AdminDeleteProductController", value = "/AdminDeleteProductController")
public class AdminDeleteProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int colorId = Integer.parseInt(request.getParameter("colorId"));

        ProductAdminService productAdminService = new ProductAdminService();
        ProductModel productModel = new ProductModel();
        productModel.setId(productId);
        productModel.setColorId(colorId);

        try {
            boolean result = productAdminService.deleteProduct(productModel);
            if (result){
                response.sendRedirect("ProductAdminController");

            }
            else {
                response.sendRedirect("ProductAdminController");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
