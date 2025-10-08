package com.Phamducdoanh.Backend.Exeption;

import org.springframework.http.HttpStatus;

//Hàm tạo code error
public enum ErrorCode {
    CATEGORY_NOTFOUND(404, "Không tìm thấy nhóm sản phẩm","error" , HttpStatus.NOT_FOUND),
    PRODUCTNAME_NOTFOUND(404,"Không tìm thấy sản phẩm phù hợp","error" , HttpStatus.NOT_FOUND),
    FINDBYCATEGORY_NOTFOUND(404,"Không tìm thấy sản phẩm phù hợp","error" , HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1003, "Tên sản phẩm phải nhiều hơn 10 ký tự","error" , HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "Mật khẩu phải nhiều hơn 6 ký tự","error" , HttpStatus.BAD_REQUEST),
    USER_EXISTED(1004, "Tài khoản đã tồn tại", "error" ,HttpStatus.BAD_REQUEST),
    PASSWORK_MISSMATCH(1004, "Mật khẩu cũ không chính xác", "error" , HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_REQUIRED(1005, "Không được để trống", "error" , HttpStatus.BAD_REQUEST),
    USER_NOTFOUND(404,"Không tìm thấy tài khoản phù hợp", "error" , HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(404, "User không tồn tại", "error" , HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", "unauthoried" , HttpStatus.UNAUTHORIZED),
    ;

    private final int code;
    private final String message;
    private final String status;
    private final HttpStatus  httpStatus;

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    ErrorCode(int code, String message, String status, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.httpStatus = httpStatus;
    }
}
