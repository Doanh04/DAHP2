package com.Phamducdoanh.Backend.Maper;

import com.Phamducdoanh.Backend.DTO.Response.CartItemResponseDTO;
import com.Phamducdoanh.Backend.DTO.Response.CartResponseDTO;
import com.Phamducdoanh.Backend.entity.CartEntity;
import com.Phamducdoanh.Backend.entity.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMaper {
    //default để thêm logic tính toán và tạo URL ảnh
    default CartItemResponseDTO toCartItemResponseDTO(CartItemEntity cartItemEntity) {
        if (cartItemEntity == null) {
            return null;
        }
        double totalPrice = cartItemEntity.getUnitPrice() *  cartItemEntity.getQuantity();

        return CartItemResponseDTO.builder()
                .cartItemId(cartItemEntity.getCartItemId())
                .quantity(cartItemEntity.getQuantity())
                //nhận thông tin từ product
                .productId(cartItemEntity.getProduct().getProductId())
                .productName(cartItemEntity.getProduct().getProductName())
                .unitPrice(cartItemEntity.getUnitPrice())

                .totalPrice(totalPrice)

                .imageUrl("/product/images" + cartItemEntity.getProduct().getProductId())
                .build();
    }
    List<CartItemResponseDTO> toCartItemResponseDTOList(List<CartItemEntity> entityList);

//    Map Cartentity sang Cartresponse
    @Mapping(target = "cartId", source = "cartId")
    @Mapping(target = "createDate", source = "createDate")
    @Mapping(target = "items", source = "cartItemEntityList")
    @Mapping(target = "subTotal", ignore = true)
    CartResponseDTO toCartResponseDTO(CartEntity entity);
}
