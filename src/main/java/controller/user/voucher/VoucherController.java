package controller.user.voucher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.VoucherModel;
import service.user.voucher.VoucherService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "VoucherController", value = "/VoucherController")
public class VoucherController extends HttpServlet {
    private final VoucherService voucherService = new VoucherService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<VoucherModel> listVoucherShipping = voucherService.getAllVoucherShipping();
        List<VoucherModel> listVoucherItems = voucherService.getAllVoucherItems();

        System.out.println("List Voucher Shipping: " + listVoucherShipping);
        System.out.println("List Voucher Items: " + listVoucherItems);

        request.setAttribute("listVoucherShipping", listVoucherShipping);
        request.setAttribute("listVoucherItems", listVoucherItems);

        request.getRequestDispatcher("shoppingCart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
