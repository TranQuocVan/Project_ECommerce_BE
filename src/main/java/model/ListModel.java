package model;

import java.util.ArrayList;
import java.util.List;

public class ListModel<T> {
    private List<T> lists;

    // Khởi tạo danh sách trong constructor
    public ListModel() {
        this.lists = new ArrayList<>();
    }

    public List<T> getLists() {
        return lists;
    }
}
