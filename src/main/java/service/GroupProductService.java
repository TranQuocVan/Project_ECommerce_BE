package service;

import database.GroupProductDao;
import database.ProductCategoryDao;
import model.GroupProductModel;
import model.ProductCategoryModel;

import java.util.List;

public class GroupProductService {
    public List<GroupProductModel> getAllProductCategory(){
        GroupProductDao dao = new GroupProductDao();
        return dao.getAllGroupProduct();
    }

    public boolean addGroupProduct(GroupProductModel groupProductModel){
        GroupProductDao dao = new GroupProductDao();
        return dao.addGroupProduct(groupProductModel);
    }


}
