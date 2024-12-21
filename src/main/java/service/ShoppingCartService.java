package service;

import database.ShoppingCartItemsDao;
import model.ShoppingCartItems;

import java.util.List;

public class ShoppingCartService {
    public boolean addProductToShoppingCart(int orderId, int sizeId){
        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        return shoppingCartItemsDao.addProductToShoppingCart(orderId, sizeId);
    }
    public List<ShoppingCartItems> getAllShoppingCartItems(int orderId){
        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        return shoppingCartItemsDao.getAllShoppingCartItems(orderId);
    }

}
