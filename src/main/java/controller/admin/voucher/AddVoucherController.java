package controller.admin.voucher;

import database.TypeVoucherDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TypeVoucherModel;
import model.VoucherModel;
import service.user.voucher.TypeVoucherService;
import service.user.voucher.VoucherService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddVoucherController", value = "/AddVoucherController")
public class AddVoucherController extends HttpServlet {
    private final TypeVoucherService typeVoucherService = new TypeVoucherService(new TypeVoucherDAO());
    private final VoucherService voucherService = new VoucherService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TypeVoucherModel> typeVoucherList = typeVoucherService.getAllTypeVouchers();

        request.setAttribute("typeVoucherList", typeVoucherList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPages/addVoucher.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Đảm bảo đọc tiếng Việt đúng
        response.setContentType("text/html;charset=UTF-8");

        int typeVoucherId = Integer.parseInt(request.getParameter("typeVoucherId"));
        float discountPercent = Float.parseFloat(request.getParameter("discountPercent"));
        float discountMaxValue = Float.parseFloat(request.getParameter("discountMaxValue"));
        String startDate = request.getParameter("startDate"); // Format yyyy-MM-dd
        String endDate = request.getParameter("endDate");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        VoucherModel voucher = new VoucherModel();
        voucher.setTypeVoucherId(typeVoucherId);
        voucher.setDiscountPercent(discountPercent);
        voucher.setDiscountMaxValue(discountMaxValue);
        voucher.setStartDate(startDate);
        voucher.setEndDate(endDate);
        voucher.setQuantity(quantity);

        try {
            voucherService.addVoucher(voucher);
            response.sendRedirect(request.getContextPath() + "/AddVoucherController?success=true");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/AddVoucherController?error=true");
        }
    }
}
