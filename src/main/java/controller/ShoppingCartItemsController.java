package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.ListModel;
import model.ShoppingCartItems;
import model.ShoppingCartItemsModel;
import model.UserModel;
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
        OrderService orderService = new OrderService();
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");


        ShoppingCartService shoppingCartService = new ShoppingCartService();
        List<ShoppingCartItemsModel> lists = shoppingCartService.getAllShoppingCartItems(user.getId());

        float totalPrice = shoppingCartService.totalPrice(user.getId());
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#,###", symbols);
        String formattedPrice = df.format(totalPrice) + "đ";

        request.setAttribute("totalPriceFormat", formattedPrice);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("shoppingCartItemsList", lists);
        request.getRequestDispatcher("shoppingCart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         doGet(request, response);
    }
}
