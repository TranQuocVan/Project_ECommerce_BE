package controller.admin.voucher;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TypeVoucherModel;
import service.user.voucher.TypeVoucherService;
import database.TypeVoucherDAO;

import java.io.IOException;

@WebServlet(name = "AddTypeVoucherController", value = "/AddTypeVoucherController")
public class AddTypeVoucherController extends HttpServlet {

    private TypeVoucherService typeVoucherService;

    @Override
    public void init() throws ServletException {
        super.init();
        // Khởi tạo Service và DAO tại đây
        typeVoucherService = new TypeVoucherService(new TypeVoucherDAO());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPages/addTypeVoucher.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Đảm bảo đọc tiếng Việt đúng
        response.setContentType("text/html;charset=UTF-8");

        String typeName = request.getParameter("typeName");
        String description = request.getParameter("description");

        TypeVoucherModel model = new TypeVoucherModel();
        model.setTypeName(typeName);
        model.setDescription(description);

        try {
            typeVoucherService.addTypeVoucher(model);
            // Redirect lại trang form sau khi thêm thành công (hoặc redirect về danh sách)
            response.sendRedirect(request.getContextPath() + "/AddTypeVoucherController?success=true");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/AddTypeVoucherController?error=true");
        }
    }
}
