package controller; import jakarta.servlet.*; 
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException; 
@WebServlet(name = "ShoppingCartItemOrdersController", value = "/ShoppingCartItemOrdersController") 
public class ShoppingCartItemOrdersController extends HttpServlet { 

@Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { } 
@Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { } 
}
