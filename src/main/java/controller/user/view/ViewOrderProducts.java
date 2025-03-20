package controller.user.view;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.OrderModel;
import service.user.view.ViewOrderProductsService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ViewOrderProducts", value = "/ViewOrderProducts")
public class ViewOrderProducts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the "id" parameter and handle invalid input gracefully
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID");
            return;
        }

        // Initialize the service to retrieve order details
        ViewOrderProductsService viewOrderProductsService = new ViewOrderProductsService();
        try {
            // Fetch the order model using the service
            OrderModel orderModel = viewOrderProductsService.getOrder(id);


            // Set the products as a request attribute
            request.setAttribute("orderProducts", orderModel);

            // Forward the request to the JSP page for rendering
            request.getRequestDispatcher("/orderDetails.jsp").forward(request, response);
        } catch (SQLException e) {
            // Log the exception and return an internal server error
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred while fetching order details");
        }
    }

}