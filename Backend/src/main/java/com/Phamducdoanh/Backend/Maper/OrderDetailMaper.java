package com.Phamducdoanh.Backend.Maper;

import com.Phamducdoanh.Backend.DTO.Response.OrderDetailResponseDTO;
import com.Phamducdoanh.Backend.entity.OrderDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailMaper {

    @Mapping(source = "productDetail.productId", target = "productId")
    @Mapping(source = "productDetail.productName", target = "productName")
    OrderDetailResponseDTO toOrderDetailDTO(OrderDetailEntity orderDetailEntity);
}
