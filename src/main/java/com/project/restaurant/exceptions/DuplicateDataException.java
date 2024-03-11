package com.project.restaurant.exceptions;

public class DuplicateDataException extends Exception{ //Ngoại lệ trùng lặp dữ liệu
    public DuplicateDataException(String message) {
        super(message);
    }
}
