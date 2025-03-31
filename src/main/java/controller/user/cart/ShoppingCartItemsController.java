package controller.user.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.*;
import service.user.cart.ShoppingCartService;
import service.user.voucher.VoucherService;
import service.util.FormatPriceServices;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

@WebServlet(name = "ShoppingCartItemsController", value = "/ShoppingCartItemsController")
public class ShoppingCartItemsController extends HttpServlet {
    private final VoucherService voucherService = new VoucherService();
    private final ShoppingCartService shoppingCartService = new ShoppingCartService();
    private final FormatPriceServices formatPriceServices = new FormatPriceServices();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");


        List<ShoppingCartItemsModel> lists = shoppingCartService.getAllShoppingCartItems(user.getId());
        List<PaymentModel> listPaymentModels = shoppingCartService.getAllPayments();
        List<DeliveriesModel> listDeliveriesModels = shoppingCartService.getAllDeliveries();


        List<VoucherModel> listVoucherShipping = voucherService.getAllVoucherShipping();
        List<VoucherModel> listVoucherItems = voucherService.getAllVoucherItems();

        System.out.println("List Voucher Shipping: " + listVoucherShipping);
        System.out.println("List Voucher Items: " + listVoucherItems);

        request.setAttribute("listVoucherShipping", listVoucherShipping);
        request.setAttribute("listVoucherItems", listVoucherItems);

//        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
//        symbols.setGroupingSeparator('.');
//
//        DecimalFormat df = new DecimalFormat("#,###", symbols);
//        String formattedPrice = df.format(0) + "đ";

        String formattedPrice = formatPriceServices.formatPrice();
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

        List<ShoppingCartItemsModel> lists = shoppingCartService.getAllShoppingCartItems(user.getId());
        List<PaymentModel> listPaymentModels = shoppingCartService.getAllPayments();
        List<DeliveriesModel> listDeliveriesModels = shoppingCartService.getAllDeliveries();


        request.setAttribute("shoppingCartItemsList", lists);
        request.setAttribute("listPaymentModels", listPaymentModels);
        request.setAttribute("listDeliveriesModels", listDeliveriesModels);


        request.getRequestDispatcher("shoppingCart.jsp").forward(request, response);
    }


}
