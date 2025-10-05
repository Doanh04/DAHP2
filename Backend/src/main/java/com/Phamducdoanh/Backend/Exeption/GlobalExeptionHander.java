package com.Phamducdoanh.Backend.Exeption;

import com.Phamducdoanh.Backend.DTO.Response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExeptionHander {
//     Bắt và xử lý LỖI NGHIỆP VỤ (AppException)
    @ExceptionHandler(value = AppExeption.class)
    public ResponseEntity<ErrorResponse> handleAppExeption(AppExeption e) {
        //Lấy ErrorCode từ AppException bị ném ra
        ErrorCode errorCode = e.getErrorCode();

        //Tạo đối tượng phản hồi từ thông tin của ErrorCode DTO
       ErrorResponse errorResponse = new ErrorResponse(
               errorCode.getCode(),
               errorCode.getMessage(),
               errorCode.getMessage()
       );

        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }
}
