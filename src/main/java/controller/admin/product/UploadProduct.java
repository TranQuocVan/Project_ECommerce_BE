package controller.admin.product;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.InputStream;

import service.user.product.ProductService;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 5,      // 5MB
        maxRequestSize = 1024 * 1024 * 10   // 10MB
)
@WebServlet(name = "uploadProduct", value = "/uploadProduct")
public class UploadProduct extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String nameProduct = request.getParameter("name");
        String quantityParam = request.getParameter("quantity");
        String priceParam = request.getParameter("price");
        String category = request.getParameter("category");
        Part filePart = request.getPart("image");

        // Validate input
        if (nameProduct == null || quantityParam == null || priceParam == null || filePart == null) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Invalid data\"}");
            return;
        }

        int quantity;
        float price;


        try {
            quantity = Integer.parseInt(quantityParam);
            price = Float.parseFloat(priceParam);

            if (quantity <= 0 || price <= 0) {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"Quantity and price must be positive.\"}");
                return;
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Invalid quantity or price format.\"}");
            return;
        }

        InputStream inputStream = filePart.getInputStream();

        int row = ProductService.saveProduct(nameProduct, quantity, inputStream, price, category);


        String message = (row > 0)
                ? "{\"status\":\"success\",\"message\":\"File uploaded and saved into database.\"}"
                : "{\"status\":\"error\",\"message\":\"Failed to upload file.\"}";

        response.getWriter().write(message);
    }


}