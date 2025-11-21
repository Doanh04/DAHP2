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
    NOT_ROLES(1007,"Không đủ quyền truy cập", "error" , HttpStatus.FORBIDDEN),
    NOT_FIND_ROLE(1008,"Không tìm thấy role", "error" , HttpStatus.FORBIDDEN),
    INVALID_QUANTITY(1009, "Số lượng không hợp lệ", "error",HttpStatus.BAD_REQUEST),
    CART_ITEM_NOTFOUND(1010, "Không tìm thấy sản phẩm trong giỏ hàng", "error", HttpStatus.NOT_FOUND),
    ACCESS_DENIED(1011, "Truy cập bị từ chối", "error", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND_IN_CART(1011, "Sản phẩm không có trong giỏ hàng", "error", HttpStatus.NOT_FOUND),
    CART_NOT_FOUND(1012,"Không tìm thấy giỏ hàng", "error" , HttpStatus.NOT_FOUND),
    ORDER_NOTFOUND(1013,"Không tìm thấy đơn hàng", "error" , HttpStatus.NOT_FOUND),
    ORDER_ALREADY_PAID(1014, "Đơn hàng đang chờ thanh toán", "error" , HttpStatus.BAD_REQUEST),
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
