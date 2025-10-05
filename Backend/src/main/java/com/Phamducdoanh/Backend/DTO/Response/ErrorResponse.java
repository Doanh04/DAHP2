package com.Phamducdoanh.Backend.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE)
@Builder

//Cấu trúc lỗi trả về Global Exeption hander
public class ErrorResponse {
    int code;
    String message;
    String status;
}
