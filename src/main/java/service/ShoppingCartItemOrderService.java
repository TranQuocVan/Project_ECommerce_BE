package service;

import database.ShoppingCartItemOrdersDao;
import model.ShoppingCartItemOrders;

import java.sql.Timestamp;

public class ShoppingCartItemOrderService {
    public boolean addShoppingCartItemOrders(ShoppingCartItemOrders order){
        ShoppingCartItemOrdersDao dao = new ShoppingCartItemOrdersDao();
        return dao.addShoppingCartItemOrders(order);
    }
    public int getOrderId (int userId , Timestamp time) {
        ShoppingCartItemOrdersDao dao = new ShoppingCartItemOrdersDao();
        return dao.getOrderId(userId,time);
    }

    }
