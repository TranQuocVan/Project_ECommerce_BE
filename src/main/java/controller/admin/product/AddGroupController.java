package controller.admin.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.GroupProductModel ;
import service.user.product.GroupProductService;
import service.user.product.ProductCategoryService;

import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "AddGroupController", value = "/AddGroupController")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 5,      // 5MB
        maxRequestSize = 1024 * 1024 * 10   // 10MB
)
public class AddGroupController extends HttpServlet {

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
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        if (name != null && !name.isEmpty()) {
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream inputStream = filePart.getInputStream()) {
                    GroupProductModel groupProductModel = new GroupProductModel(name, inputStream, description);
                    GroupProductService gps = new GroupProductService();
                    boolean result = gps.addGroupProduct(groupProductModel);
                    request.setAttribute("GroupProductResults", result ? "Group product added successfully!" : "Failed to add group product.");
                }
            }
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPages/admin.jsp");
        dispatcher.forward(request, response);
    }
}