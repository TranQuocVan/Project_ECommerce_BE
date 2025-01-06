package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.ShoppingCartService;

import java.io.IOException;

@WebServlet(name = "DeleteCartItemController", value = "/DeleteCartItemController")
public class DeleteCartItemController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        int sizeId = Integer.parseInt(request.getParameter("sizeId"));


        ShoppingCartService shoppingCartService = new ShoppingCartService();
        if (shoppingCartService.deleteProductToShoppingCart(sizeId, user.getId())) {
            request.getRequestDispatcher("ShoppingCartItemsController").forward(request, response);
        }
        request.getRequestDispatcher("ShoppingCartItemsController").forward(request, response);

    }
}
