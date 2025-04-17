package controller.admin.voucher;

import database.TypeVoucherDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TypeVoucherModel;
import service.user.voucher.TypeVoucherService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManagerTypeVoucherController", value = "/ManagerTypeVoucherController")
public class ManagerTypeVoucherController extends HttpServlet {

    private final TypeVoucherService typeVoucherService = new TypeVoucherService(new TypeVoucherDAO());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TypeVoucherModel> typeVoucherList = typeVoucherService.getAllTypeVouchers();

        request.setAttribute("typeVoucherList", typeVoucherList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPages/managerTypeVoucher.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            typeVoucherService.deleteTypeVoucher(id);
            response.getWriter().write("success"); // AJAX nhận
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String typeName = request.getParameter("typeName");
            String description = request.getParameter("description");

            TypeVoucherModel updated = new TypeVoucherModel(id, typeName, description);
            typeVoucherService.updateTypeVoucher(updated);
            response.getWriter().write("success"); // AJAX nhận
        }
    }

}
