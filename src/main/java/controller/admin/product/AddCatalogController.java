package controller.admin.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.ProductCategoryModel;
import model.UserModel;
import service.log.LogService;
import service.user.product.ProductCategoryService;

import java.io.IOException;

@WebServlet(name = "AddCatalogController", value = "/AddCatalogController")
public class AddCatalogController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String ipAddress = request.getRemoteAddr();
        boolean success = false;
        String message = "Failed to add product category.";

        if (name != null && !name.isEmpty()) {
            ProductCategoryModel categoryModel = new ProductCategoryModel(name, description);
            ProductCategoryService pcs = new ProductCategoryService();
            success = pcs.addProductCategory(categoryModel);
            message = success ? "Product category added successfully!" : "Failed to add product category.";
        }

        // Ghi log
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        LogService.adminAddCatalog(user.getId(), name, ipAddress);

        // Trả về JSON để AJAX xử lý
        response.getWriter().write("{\"success\": " + success + ", \"message\": \"" + message + "\"}");
    }
}
