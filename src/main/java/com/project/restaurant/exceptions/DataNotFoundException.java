package com.project.restaurant.exceptions;

public class DataNotFoundException extends Exception{ // => Có tác dụng kiểm tra Exception trả về là loại Exception nào

    //Thực thi lại phương thức của lớp cha
    public DataNotFoundException(String message) {
        super(message);
    }
}
