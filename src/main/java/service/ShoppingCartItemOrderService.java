package service;

import database.ShoppingCartItemOrdersDao;
import model.ShoppingCartItemOrders;

import java.sql.Timestamp;

public class ShoppingCartItemOrderService {
    public boolean addShoppingCartItemOrders(ShoppingCartItemOrders order, int userId){
        ShoppingCartItemOrdersDao dao = new ShoppingCartItemOrdersDao();
        return dao.addShoppingCartItemOrders(order,userId);
    }


    }
