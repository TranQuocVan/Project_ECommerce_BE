package controller.admin.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.ProductCategoryModel;
import service.user.product.ProductCategoryService;

import java.io.IOException;

@WebServlet(name = "AddCatalogController", value = "/AddCatalogController")
public class AddCatalogController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

                if (name != null && !name.isEmpty()) {
            ProductCategoryModel categoryModel = new ProductCategoryModel(name, description);
            ProductCategoryService pcs = new ProductCategoryService();
            boolean result = pcs.addProductCategory(categoryModel);
            request.setAttribute("ProductResults", result ? "Product category added successfully!" : "Failed to add product category.");
                }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPages/admin.jsp");
        dispatcher.forward(request, response);

    }
}
