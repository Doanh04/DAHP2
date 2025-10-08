package com.Phamducdoanh.Backend.Service.User;

import com.Phamducdoanh.Backend.DTO.Request.ChangePassDTO;
import com.Phamducdoanh.Backend.DTO.Request.UserDTO;
import com.Phamducdoanh.Backend.DTO.Response.UserResponse;
import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Maper.UserMaper;
import com.Phamducdoanh.Backend.Repository.RoleRepository;
import com.Phamducdoanh.Backend.Repository.UserRepository;
import com.Phamducdoanh.Backend.entity.RolesEntity;
import com.Phamducdoanh.Backend.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccoutUserService {
    final UserRepository userRepository;
    final UserMaper userMaper;
    final RoleRepository roleRepository;

    public UserResponse createUser(UserDTO userDTO) {
        if(userRepository.existsByUsername(userDTO.getUsername())){
            throw new AppExeption(ErrorCode.USER_EXISTED);
        }
        UserEntity userEntity = userMaper.toUserDTO(userDTO);

        RolesEntity defaultRole = roleRepository.findByRoleName("customer");
        userEntity.setRole(defaultRole);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));


        return userMaper.toUserResponse(userRepository.save(userEntity));
    }
    //    Hàm cập nhật tài khoản
    public UserResponse updateUser(String UserId, UserDTO userDTO) {
        UserEntity userEntity = userRepository.findByUserId(UserId);
        if(userEntity == null){
            throw new AppExeption(ErrorCode.USER_NOTFOUND);
        }
        userMaper.updateUser(userDTO, userEntity);
        UserEntity updatedEntity = userRepository.save(userEntity);
        return userMaper.toUserResponse(updatedEntity);
    }
    //    Hàm đổi mật khẩu
    public UserResponse changePassword(String UserId, ChangePassDTO changePassDTO) {
        UserEntity userEntity = userRepository.findByUserId(UserId);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        Kiểm tra mật khẩu cũ với mật khẩu đã được mã hóa trong db
        if(!passwordEncoder.matches(changePassDTO.getCurrentPassword(), userEntity.getPassword())){
            throw new AppExeption(ErrorCode.PASSWORK_MISSMATCH);
        }
//        Kiểm tra mật khẩu mới tránh null hoặc rỗng
        if(changePassDTO.getNewPassword() == null || changePassDTO.getNewPassword().isEmpty()){
            throw new AppExeption(ErrorCode.NEW_PASSWORD_REQUIRED);
        }
//        Mã hóa mật khẩu mới và lưu vào db
        userEntity.setPassword(passwordEncoder.encode(changePassDTO.getNewPassword()));
        UserEntity updated =  userRepository.save(userEntity);
        return userMaper.toUserResponse(updated);
    }
}
