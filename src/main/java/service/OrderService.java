package service;

import database.OrderDao;
import model.Order;
import model.OrderModel;

import java.util.List;

public class OrderService {
    public boolean addOrder(Order orderModel) {
        OrderDao orderDao = new OrderDao();
        return orderDao.addOrder(orderModel);
    }
//    public boolean checkOrder(int userId) {
//        OrderDao orderDao = new OrderDao();
//        return orderDao.checkOrder(userId);
//    }
//    public int getOrderId(int userId) {
//        OrderDao orderDao = new OrderDao();
//        return orderDao.getOrderId(userId);
//    }
public List<OrderModel> getAllOrders(int userId){
        OrderDao orderDao = new OrderDao();
        return orderDao.getAllOrders(userId);
}

}
