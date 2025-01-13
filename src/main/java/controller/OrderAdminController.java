package controller;

import database.OrderAdminDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.*;
import service.OrderAdminService;
import service.ShoppingCartService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "OrderAdminController", value = "/OrderAdminController")
public class OrderAdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCartService shoppingCartService = new ShoppingCartService();
        OrderAdminService orderAdminService = new OrderAdminService();


        List<PaymentModel> listPaymentModels = shoppingCartService.getAllPayments();
        List<DeliveriesModel> listDeliveriesModels = shoppingCartService.getAllDeliveries();
        List<StatusAdminModel> statusAdminModels = orderAdminService.getAllStatus();

        request.setAttribute("listPaymentModels", listPaymentModels);
        request.setAttribute("listDeliveriesModels", listDeliveriesModels);
        request.setAttribute("statusAdminModels", statusAdminModels);

        request.getRequestDispatcher("adminPages/managerOrderWithFilter.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int paymentId = Integer.parseInt(request.getParameter("paymentId"));
        int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
        int statusTypeId =  Integer.parseInt(request.getParameter("statusTypeId")) ;
        String dateString = request.getParameter("orderDate");
        Date sqlDate = null;

        if (dateString != null && !dateString.isEmpty()) {
            try {
                // Định dạng ngày mong muốn (yyyy-MM-dd)
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = dateFormat.parse(dateString);

                // Chuyển đổi java.util.Date sang java.sql.Date
                sqlDate = new Date(utilDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

            OrderAdminService orderAdminService = new OrderAdminService();
            try {
               List<OrderAdminModel> od =  orderAdminService.getAllOrders(paymentId, deliveryId, sqlDate, statusTypeId);
                ShoppingCartService shoppingCartService = new ShoppingCartService();
                List<PaymentModel> listPaymentModels = shoppingCartService.getAllPayments();
                List<DeliveriesModel> listDeliveriesModels = shoppingCartService.getAllDeliveries();
                List<StatusAdminModel> statusAdminModels = orderAdminService.getAllStatus();


                request.setAttribute("listOrderAdminModel", od);
                request.setAttribute("listPaymentModels", listPaymentModels);
                request.setAttribute("listDeliveriesModels", listDeliveriesModels);
                request.setAttribute("statusAdminModels", statusAdminModels);


                request.getRequestDispatcher("adminPages/managerOrderWithFilter.jsp").forward(request, response);
               //managerOrderWithFilter
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

}
