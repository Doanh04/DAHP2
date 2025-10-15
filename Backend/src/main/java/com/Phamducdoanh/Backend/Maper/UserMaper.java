package com.Phamducdoanh.Backend.Maper;

import com.Phamducdoanh.Backend.DTO.Request.UserDTO;
import com.Phamducdoanh.Backend.DTO.Response.UserResponse;
import com.Phamducdoanh.Backend.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring",uses = {PermisionMaper.class, RoleMappingHelper.class})
public interface UserMaper {
//    MAP Request sang entity thực hiện create và update dữ liệu
    UserEntity toUserDTO(UserDTO userDTO);
//    Map entity sang Response thực hiện trả dữ liệu
    @Mapping(source = "userId", target = "userId")
    UserResponse toUserResponse(UserEntity userEntity);
//    Map update bỏ qua, user id và username pass để cập nhật thông tin tài khoản
    @Mapping(target = "userId", ignore = true)     // Bỏ qua ID
    @Mapping(target = "username", ignore = true)   // Bỏ qua Username
    @Mapping(target = "createdAt", ignore = true)  // Bỏ qua ngày tạo
    @Mapping(target = "cart", ignore = true)       // Bỏ qua quan hệ (nếu có)
    @Mapping(target = "orders", ignore = true)      // Bỏ qua quan hệ
    @Mapping(target = "password", ignore = true)
    void updateUser(UserDTO userDTO, @MappingTarget UserEntity userEntity);

    List<UserResponse> toUserResponseList(List<UserEntity> userEntityList);
}
