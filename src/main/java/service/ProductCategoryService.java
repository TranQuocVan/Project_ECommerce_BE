package service;


import database.ProductCategoryDao;

import model.PageModel;
import model.ProductCategoryModel;
import model.ProductModel;

import java.util.List;

public class ProductCategoryService {

    public List<ProductCategoryModel> getAllProductCategory(){
        ProductCategoryDao dao = new ProductCategoryDao();
        return dao.getAllProductCategory();
    }


    public boolean addProductCategory(ProductCategoryModel productCategoryModel) {
        ProductCategoryDao dao = new ProductCategoryDao();
        return dao.addProductCategory(productCategoryModel);

    }

    public PageModel<ProductModel> getPageNumber( String categoryName,int pageNo, int pageSize){
        ProductCategoryDao dao = new ProductCategoryDao();

        int totalRecords = dao.countProductsByCategoryName(categoryName);
        List<ProductModel> products = dao.getProductByCategoryName(categoryName, pageNo, pageSize);

        PageModel<ProductModel> pageModel = new PageModel<>();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        pageModel.setTotalRecords(totalRecords);
        pageModel.setItems(products);

        return pageModel;

    }


}
