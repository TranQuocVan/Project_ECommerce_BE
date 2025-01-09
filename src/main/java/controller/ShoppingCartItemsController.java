package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.*;
import service.OrderService;
import service.ShoppingCartService;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ShoppingCartItemsController", value = "/ShoppingCartItemsController")
public class ShoppingCartItemsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");


        ShoppingCartService shoppingCartService = new ShoppingCartService();
        List<ShoppingCartItemsModel> lists = shoppingCartService.getAllShoppingCartItems(user.getId());
        List<PaymentModel> listPaymentModels = shoppingCartService.getAllPayments();
        List<DeliveriesModel> listDeliveriesModels = shoppingCartService.getAllDeliveries();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#,###", symbols);
        String formattedPrice = df.format(0) + "đ";

        request.setAttribute("totalPriceFormat", formattedPrice);
        request.setAttribute("totalPrice", formattedPrice);
        request.setAttribute("shoppingCartItemsList", lists);
        request.setAttribute("listPaymentModels", listPaymentModels);
        request.setAttribute("listDeliveriesModels", listDeliveriesModels);
        request.getRequestDispatcher("shoppingCart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        int sizeId =Integer.parseInt(request.getParameter("sizeId")) ;


        ShoppingCartService shoppingCartService = new ShoppingCartService();
        List<ShoppingCartItemsModel> lists = shoppingCartService.getAllShoppingCartItems(user.getId());
        List<PaymentModel> listPaymentModels = shoppingCartService.getAllPayments();
        List<DeliveriesModel> listDeliveriesModels = shoppingCartService.getAllDeliveries();


        float totalPrice = shoppingCartService.totalPrice(sizeId);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#,###", symbols);
        String formattedPrice = df.format(totalPrice) + "đ";

        request.setAttribute("totalPriceFormat", formattedPrice);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("shoppingCartItemsList", lists);
        request.setAttribute("listPaymentModels", listPaymentModels);
        request.setAttribute("listDeliveriesModels", listDeliveriesModels);


        request.getRequestDispatcher("shoppingCart.jsp").forward(request, response);
    }


}
