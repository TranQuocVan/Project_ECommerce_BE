package service;

import database.OrderDao;
import database.ProductDao;
import database.ShoppingCartItemOrdersDao;
import database.StatusDao;
import model.OrderModel;
import model.ProductModel;
import model.ShoppingCartItemOrderModel;
import model.ShoppingCartItemsModel;

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
                // Thêm sản phẩm vào danh sách sản phẩm của đơn hàng
                products.add(product);
            }
        }

        // Gán danh sách sản phẩm cho đơn hàng
        orderModel.setProductModels(products);

        return orderModel;
    }



}
