package model;

import java.util.List;

public class PageModel<T> {
    private int pageNo;
    private int pageSize;
    private int totalRecords;
    private List<T> items;


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    // Utility methods for pagination
    public int getTotalPages() {
        return (int) Math.ceil((double) totalRecords / pageSize); // Calculate total pages
    }

    public boolean hasPreviousPage() {
        return pageNo > 1; // Check if there is a previous page
    }

    public boolean hasNextPage() {
        return pageNo < getTotalPages(); // Check if there is a next page
    }



}
