package service;

import database.OrderDao;
import model.Order;
import model.OrderModel;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    public int addOrder(Order orderModel) {
        OrderDao orderDao = new OrderDao();
        return orderDao.addOrder(orderModel);
    }

public List<OrderModel> getAllOrders(int userId){
        OrderDao orderDao = new OrderDao();
        return orderDao.getAllOrders(userId);
}
    public float calculateTotalPrice(List<Integer> listSizeId, int userId, int paymentId) throws SQLException {
        OrderDao orderDao = new OrderDao();
        return orderDao.calculateTotalPrice(listSizeId, userId, paymentId);

    }

}
