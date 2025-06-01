package controller.admin.voucher;

import database.TypeVoucherDAO;
import database.VoucherDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.TypeVoucherModel;
import model.VoucherModel;
import service.admin.voucher.TypeVoucherAdminService;
import service.admin.voucher.VoucherAdminService;
import service.user.voucher.VoucherService;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "ManagerVoucherController", value = "/ManagerVoucherController")
public class ManagerVoucherController extends HttpServlet {
    private final VoucherService voucherService = new VoucherService();
    private final VoucherAdminService voucherAdminService = new VoucherAdminService(new VoucherDAO());
    private final TypeVoucherAdminService typeVoucherService = new TypeVoucherAdminService(new TypeVoucherDAO());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tất cả loại voucher
        List<TypeVoucherModel> allTypeVouchers = typeVoucherService.getAllTypeVouchers();

        // Lấy tham số typeVoucherId từ request
        String typeIdParam = request.getParameter("typeVoucherId");

        // Lấy danh sách voucher theo loại hoặc tất cả nếu không có loại cụ thể
        List<VoucherModel> vouchers;
        if (typeIdParam != null && !typeIdParam.equals("all")) {
            // Nếu có loại voucher được chọn, lọc theo loại đó
            int typeVoucherId = Integer.parseInt(typeIdParam); // Chuyển sang int chỉ khi cần sử dụng trong dịch vụ
            vouchers = voucherService.getVouchersByTypeVoucher(typeVoucherId);
        } else {
            // Nếu không có loại voucher, lấy tất cả
            vouchers = voucherService.getAllVouchers();
        }

        // Sắp xếp danh sách voucher theo voucherId
        vouchers.sort((v1, v2) -> Integer.compare(v2.getVoucherId(), v1.getVoucherId()));

        // Tạo map mô tả các loại voucher
        Map<Integer, String> typeVoucherDescriptions = typeVoucherService.getTypeVoucherDescriptions(vouchers);

        // Đưa thông tin vào request để hiển thị trên JSP
        request.setAttribute("vouchers", vouchers);
        request.setAttribute("typeVoucherDescriptions", typeVoucherDescriptions);
        request.setAttribute("allTypeVouchers", allTypeVouchers);
        request.setAttribute("selectedTypeId", typeIdParam);  // Đảm bảo selectedTypeId là kiểu String

        // Chuyển hướng đến trang JSP
        request.getRequestDispatcher("/adminPages/managerVoucher.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int voucherId = Integer.parseInt(request.getParameter("voucherId"));
            voucherAdminService.deleteVoucher(voucherId);
            response.getWriter().write("success"); // AJAX nhận
        } else if ("update".equals(action)) {
            int voucherId = Integer.parseInt(request.getParameter("voucherId"));
            int typeVoucherId = Integer.parseInt(request.getParameter("typeVoucherId"));
            float discountPercent = Float.parseFloat(request.getParameter("discountPercent"));
            float discountMaxValue = Float.parseFloat(request.getParameter("discountMaxValue"));
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            // Tạo đối tượng VoucherModel mới (giả sử bạn có constructor phù hợp)
            VoucherModel updatedVoucher = new VoucherModel();
            updatedVoucher.setVoucherId(voucherId);
            updatedVoucher.setTypeVoucherId(typeVoucherId);
            updatedVoucher.setDiscountPercent(discountPercent);
            updatedVoucher.setDiscountMaxValue(discountMaxValue);
            updatedVoucher.setStartDate(startDate);
            updatedVoucher.setEndDate(endDate);
            updatedVoucher.setQuantity(quantity);

            voucherAdminService.updateVoucher(updatedVoucher);
            response.getWriter().write("success"); // AJAX nhận
        }
    }
}
