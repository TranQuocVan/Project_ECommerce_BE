package service;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import database.OrderDao;
import model.OrderModel;

public class OrderService {
    public boolean addOrder(OrderModel orderModel) {
        OrderDao orderDao = new OrderDao();
        return orderDao.addOrder(orderModel);
    }
    public boolean checkOrder(int userId) {
        OrderDao orderDao = new OrderDao();
        return orderDao.checkOrder(userId);
    }
    public int getOrderId(int userId) {
        OrderDao orderDao = new OrderDao();
        return orderDao.getOrderId(userId);
    }
}
