package controller; import jakarta.servlet.*; 
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException; 
@WebServlet(name = "GetProductByCategoryNameController", value = "/GetProductByCategoryNameController") 
public class GetProductByCategoryNameController extends HttpServlet { 

@Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { } 
@Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { } 
}