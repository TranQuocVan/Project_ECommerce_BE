package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.GroupProductModel;
import model.ProductCategoryModel;
import service.GroupProductService;
import service.ProductCategoryService;

import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "AddCategoryAndGroupController", value = "/AddCategoryAndGroupController")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 5,      // 5MB
        maxRequestSize = 1024 * 1024 * 10   // 10MB
)
public class AddCategoryAndGroupController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductCategoryService pcs = new ProductCategoryService();
        request.setAttribute("ListProductCategory", pcs.getAllProductCategory());
        GroupProductService gps = new GroupProductService();
        request.setAttribute("ListGroupProduct", gps.getAllProductCategory());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPages/addCategoryAndGroup.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String addGroupProduct = request.getParameter("AddGroupProduct");
        String addProductCategory = request.getParameter("AddProductCategory");

        if (addGroupProduct != null && !addGroupProduct.isEmpty()) {
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream inputStream = filePart.getInputStream()) {
                    GroupProductModel groupProductModel = new GroupProductModel(addGroupProduct, inputStream);
                    GroupProductService gps = new GroupProductService();
                    boolean result = gps.addGroupProduct(groupProductModel);
                    request.setAttribute("GroupProductResults", result ? "Group product added successfully!" : "Failed to add group product.");
                }
            }
        }

        if (addProductCategory != null && !addProductCategory.isEmpty()) {
            ProductCategoryModel categoryModel = new ProductCategoryModel(addProductCategory);
            ProductCategoryService pcs = new ProductCategoryService();
            boolean result = pcs.addProductCategory(categoryModel);
            request.setAttribute("ProductResults", result ? "Product category added successfully!" : "Failed to add product category.");
        }

        doGet(request, response);
    }
}