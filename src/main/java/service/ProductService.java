package service;


import database.ProductDao;
import model.Product;

import java.io.InputStream;

public class ProductService {
    public static int saveProduct(String nameProduct, int quantity, InputStream file, float price, String category) {
//        ProductDao productDao = new ProductDao();
//        int idProductCategory = productDao.getCategoryIDByName(category);
//
//        Product product = new Product(nameProduct,price,quantity,idProductCategory,file);
//
//        return productDao.addNewProduct(product);
        return 0;
    }

}
