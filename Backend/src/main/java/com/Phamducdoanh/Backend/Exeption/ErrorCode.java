package com.Phamducdoanh.Backend.Exeption;

import org.springframework.http.HttpStatus;

//Hàm tạo code error
public enum ErrorCode {
    CATEGORY_NOTFOUND(1001, "Không tìm thấy nhóm sản phẩm", HttpStatus.NOT_FOUND),
    PRODUCTNAME_NOTFOUND(404,"Không tìm thấy sản phẩm phù hợp",  HttpStatus.NOT_FOUND),
    FINDBYCATEGORY_NOTFOUND(404,"Không tìm thấy sản phẩm phù hợp",  HttpStatus.NOT_FOUND),
    ;

    private final int code;
    private final String message;
    private final HttpStatus  httpStatus;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
