package service;

import database.ShoppingCartItemsDao;
import model.ShoppingCartItems;
import model.ShoppingCartItemsModel;

import java.util.List;

public class ShoppingCartService {
    public boolean addProductToShoppingCart( int quantity, int sizeId, int userId){
        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        return shoppingCartItemsDao.addProductToShoppingCart(quantity, sizeId, userId);
    }
    public List<ShoppingCartItemsModel> getAllShoppingCartItems(int userId){
        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        return shoppingCartItemsDao.getAllShoppingCartItems(userId);
    }
    public float totalPrice (int userId){
        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        return shoppingCartItemsDao.totalPrice(userId);
    }

}
