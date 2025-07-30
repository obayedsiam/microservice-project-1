package com.example.Library.response;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private List<T> data; // List of data items
    private int pageNumber;  // Current page number
    private int pageSize;    // Number of items per page
    private long totalElements; // Total number of records
    private int totalPages;  // Total number of pages
    private boolean last;    // Is this the last page?

    public PaginatedResponse(List<T> data){
        this.data = data;
        this.pageNumber = 0;
        this.pageSize = data.size();
        this.totalElements = data.size();
        this.totalPages = 1;
        this.last = true;
    }
    // Constructor
    public PaginatedResponse(List<T> data, int pageNumber, int pageSize, long totalElements, int totalPages, boolean last) {
        this.data = data;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }
}

