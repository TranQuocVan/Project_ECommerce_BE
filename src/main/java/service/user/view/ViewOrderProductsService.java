package service.user.view;

import database.OrderDao;
import database.ProductDao;
import database.ShoppingCartItemOrdersDao;
import database.StatusDao;
import model.OrderModel;
import model.ProductModel;
import model.ShoppingCartItemOrderModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewOrderProductsService {

    public OrderModel getOrder(int id) throws SQLException, SQLException {
        OrderDao orderDao = new OrderDao();
        OrderModel orderModel = orderDao.getOrderById(id);

        // Lấy các mục trong giỏ hàng từ bảng shoppingcartitemsorder
        ShoppingCartItemOrdersDao shoppingCartItemOrdersDao = new ShoppingCartItemOrdersDao();
        List<ShoppingCartItemOrderModel> shoppingCartItemsModels = shoppingCartItemOrdersDao.getShoppingCartItemsByOrderId(id);

        StatusDao statusDao = new StatusDao();
        orderModel.setStatusModels(statusDao.getStatusesByOrderId(id));

        // Lấy thông tin sản phẩm cho từng mục trong giỏ hàng
        List<ProductModel> products = new ArrayList<>();
        for (ShoppingCartItemOrderModel shoppingCartItem : shoppingCartItemsModels) {
            int sizeId = shoppingCartItem.getSizeId();
            ProductDao productDao = new ProductDao();
            ProductModel product = productDao.getProductBySizeId(sizeId); // Lấy thông tin sản phẩm dựa trên sizeId
            if (product != null) {
                product.setPurchaseQuantity(shoppingCartItem.getQuantity());
                products.add(product);
            }
        }

        // Gán danh sách sản phẩm cho đơn hàng
        orderModel.setProductModels(products);

        return orderModel;
    }


    public OrderModel getSignAndPublishKeyById(int id){
        OrderDao orderDao = new OrderDao();
        return orderDao.getSignAndPublishKeyById(id);
    }

    public void updateOrderSignature(int orderId, String base64Signature, String base64PublicKey) throws SQLException {
        OrderDao orderDao = new OrderDao();
        orderDao.updateOrderSignature(orderId, base64Signature, base64PublicKey);
    }



}
