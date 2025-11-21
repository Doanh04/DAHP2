package com.Phamducdoanh.Backend.Maper;

import com.Phamducdoanh.Backend.DTO.Request.OrderRequestDTO;
import com.Phamducdoanh.Backend.DTO.Response.OrderResponseDTO;
import com.Phamducdoanh.Backend.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderDetailMaper.class})
public interface OrdersMaper {
    OrderEntity toOrderEntity(OrderRequestDTO orderRequestDTO);

    @Mapping(source = "paymentMethod", target = "payMethod")
    @Mapping(source = "shippingAdress", target = "shippingAddress")
    @Mapping(source = "totalAmout", target = "totalAmount")
    @Mapping(source = "orderDetailEntityList", target = "details")
    OrderResponseDTO toOrderResponseDTO(OrderEntity orderEntity);
}
