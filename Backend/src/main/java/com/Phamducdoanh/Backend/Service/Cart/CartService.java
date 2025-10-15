package com.Phamducdoanh.Backend.Service.Cart;

import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Maper.CartMaper;
import com.Phamducdoanh.Backend.Repository.CartItemRepository;
import com.Phamducdoanh.Backend.Repository.CartRepository;
import com.Phamducdoanh.Backend.Repository.ProductRepository;
import com.Phamducdoanh.Backend.Repository.UserRepository;
import com.Phamducdoanh.Backend.entity.CartEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof Jwt jwt){
            Object userIdClaim = jwt.getClaim("userId");

            if(userIdClaim == null) throw new AppExeption(ErrorCode.UNAUTHENTICATED);

        }
        throw new AppExeption(ErrorCode.UNAUTHENTICATED);
    }

    //Lấy giỏ hàng không có thì tạo mới
//    public CartEntity getOrCreateCart(){
//        String userId = getUserId();
//
//    }
}
