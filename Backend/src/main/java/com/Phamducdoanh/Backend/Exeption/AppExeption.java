package com.Phamducdoanh.Backend.Exeption;
//Hàm Tạo một lớp ngoại lệ tùy chỉnh, kế thừa từ RuntimeException, để mang theo ErrorCode khi lỗi xảy ra.
public class AppExeption extends RuntimeException {

    private final ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public AppExeption(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
