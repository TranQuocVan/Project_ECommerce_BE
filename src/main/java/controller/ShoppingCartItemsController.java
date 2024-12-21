package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.ListModel;
import model.ShoppingCartItems;
import model.UserModel;
import service.OrderService;
import service.ShoppingCartService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ShoppingCartItemsController", value = "/ShoppingCartItemsController")
public class ShoppingCartItemsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderService();
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        ListModel<ShoppingCartItems> listModel = new ListModel<>();
        List<ShoppingCartItems> shoppingCartItemsList =listModel.getShoppingCartItemsList();
        int orderId = orderService.getOrderId(user.getId());
        if (orderId > 0) {
            ShoppingCartService shoppingCartService = new ShoppingCartService();
            shoppingCartItemsList.addAll(shoppingCartService.getAllShoppingCartItems(orderId));
        }
        request.setAttribute("shoppingCartItemsList", listModel);
        request.getRequestDispatcher("shoppingCart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
