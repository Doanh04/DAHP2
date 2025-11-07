package com.Phamducdoanh.Backend.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDTO<T>{
    private List<T> content;        // Danh sách dữ liệu của trang hiện tại (ProductResponseDTO)
    private int pageNumber;         // Số trang hiện tại (Bắt đầu từ 0 hoặc 1)
    private int pageSize;           // Kích thước trang (15)
    private long totalElements;     // Tổng số item (tất cả các trang)
    private int totalPages;         // Tổng số trang có thể có
}
