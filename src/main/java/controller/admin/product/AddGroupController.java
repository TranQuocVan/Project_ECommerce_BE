package controller.admin.product;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.GroupProductModel ;
import model.UserModel;
import service.log.LogService;
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
        // Thiết lập kiểu dữ liệu trả về là JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String ipAddress = request.getRemoteAddr();

        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // Tạo đối tượng để chứa kết quả trả về
        ResponseMessage responseMessage = new ResponseMessage();

        // Kiểm tra dữ liệu đầu vào
        if (name != null && !name.isEmpty() && description != null && !description.isEmpty()) {
            Part filePart = request.getPart("image");

            // Kiểm tra file được tải lên
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream inputStream = filePart.getInputStream()) {
                    GroupProductModel groupProductModel = new GroupProductModel(name, inputStream, description);
                    GroupProductService gps = new GroupProductService();
                    boolean result = gps.addGroupProduct(groupProductModel);

                    if (result) {
                        responseMessage.setSuccess(true);
                        responseMessage.setMessage("Group product added successfully!");

                        HttpSession session = request.getSession();
                        UserModel user = (UserModel) session.getAttribute("user");
                        LogService.adminAddGroupProduct(user.getId(), name, ipAddress);

                    } else {
                        responseMessage.setSuccess(false);
                        responseMessage.setMessage("Failed to add group product.");
                    }
                } catch (IOException e) {
                    responseMessage.setSuccess(false);
                    responseMessage.setMessage("Error while processing the file.");
                }
            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("No file uploaded.");
            }
        } else {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Name or description cannot be empty.");
        }

        // Chuyển đổi đối tượng responseMessage thành JSON và gửi về client
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(responseMessage);
        response.getWriter().write(jsonResponse);


    }

    private static class ResponseMessage {
        private boolean success;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}