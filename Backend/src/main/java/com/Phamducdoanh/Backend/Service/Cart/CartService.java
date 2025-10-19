package com.Phamducdoanh.Backend.Service.Cart;

import com.Phamducdoanh.Backend.DTO.Response.CartResponseDTO;
import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Maper.CartMaper;
import com.Phamducdoanh.Backend.Repository.CartItemRepository;
import com.Phamducdoanh.Backend.Repository.CartRepository;
import com.Phamducdoanh.Backend.Repository.ProductRepository;
import com.Phamducdoanh.Backend.Repository.UserRepository;
import com.Phamducdoanh.Backend.entity.CartEntity;
import com.Phamducdoanh.Backend.entity.CartItemEntity;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import com.Phamducdoanh.Backend.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CartService {
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    ProductRepository productRepository;
    UserRepository userRepository;
    CartMaper  cartMaper;

    public String getUserId(){
        // Lấy đối tượng Authentication từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra nếu đối tượng là JwtAuthenticationToken
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            //Lấy claim 'userId' từ Jwt
            Object userIdClaim = jwt.getClaim("userId");

            //Kiểm tra và trả về userId
            if(userIdClaim instanceof String userIdString) {
                return userIdString;
            }
            // Nếu claim tồn tại nhưng không phải String (hoặc null), ngoại lệ
            throw new AppExeption(ErrorCode.UNAUTHENTICATED);
        }

        // Nếu không phải là JwtAuthenticationToken, tức là chưa xác thực
        throw new AppExeption(ErrorCode.UNAUTHENTICATED);
    }

    //Lấy giỏ hàng không có thì tạo mới
    public CartEntity getOrCreateCart(){
        String userId = getUserId();
        UserEntity user = userRepository.findByUserId(userId);
        if(user == null) throw new AppExeption(ErrorCode.USER_NOTFOUND);
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    CartEntity newCart = CartEntity.builder()
                            .user(user)
                            .createDate(new Date())
                            .build();
                    return cartRepository.save(newCart);
                });
    }
    //Thêm, cập nhật item hàng
    @Transactional
    public CartResponseDTO addOrUpdateItem(Long productId, Long quantity){
        if(quantity == null || quantity <=0){
            throw new AppExeption(ErrorCode.INVALID_QUANTITY);
        }
        CartEntity cart = getOrCreateCart();

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(()-> new AppExeption(ErrorCode.PRODUCTNAME_NOTFOUND));

        //tìm cartItem đã tồn tại
        Optional<CartItemEntity> exitingItem = cartItemRepository.findByCartAndProduct_ProductId(cart, productId);

        if(exitingItem.isPresent()){
            //cộng dồn số lượng
            CartItemEntity cartItem = exitingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        }
        else {
            CartItemEntity newItem = CartItemEntity.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .unitPrice(Double.valueOf(product.getPrice()))
                    .build();

            cart.getCartItemEntityList().add(newItem);

        }
        return getCartDetails();
    }
    //Chi tiết giỏ hàng
    public CartResponseDTO getCartDetails(){
        CartEntity cart = getOrCreateCart();

        CartResponseDTO cartResponseDTO = cartMaper.toCartResponseDTO(cart);

        double subtotal = cartResponseDTO.getItems().stream()
                .mapToDouble(item -> item.getTotalPrice() != null ? item.getTotalPrice() :0.0)
                .sum();

        cartResponseDTO.setSubTotal(subtotal);

        return cartResponseDTO;
    }
    //xóa item
    @Transactional
    public CartResponseDTO deleteItem(Long cartItemId){
        CartEntity cart = getOrCreateCart();
        CartItemEntity removeItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()->new AppExeption(ErrorCode.CART_ITEM_NOTFOUND));

        if(!removeItem.getCart().getCartId().equals(cart.getCartId())){
            throw new AppExeption(ErrorCode.ACCESS_DENIED);
        }

        cartItemRepository.delete(removeItem);

        cart.getCartItemEntityList().remove(removeItem);

        return getCartDetails();
    }
}
