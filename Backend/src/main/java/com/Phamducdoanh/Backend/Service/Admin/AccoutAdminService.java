package com.Phamducdoanh.Backend.Service.Admin;

import com.Phamducdoanh.Backend.DTO.Request.ChangePassDTO;
import com.Phamducdoanh.Backend.DTO.Request.UserDTO;
import com.Phamducdoanh.Backend.DTO.Response.UserResponse;
import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Maper.UserMaper;
import com.Phamducdoanh.Backend.Repository.RolesRepository;
import com.Phamducdoanh.Backend.Repository.UserRepository;
import com.Phamducdoanh.Backend.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccoutAdminService {
    final UserRepository userRepository;
    final UserMaper userMaper;
    final RolesRepository  rolesRepository;

//    Hàm cập nhật tài khoản
    public UserResponse updateUser(String UserId, UserDTO userDTO) {
        UserEntity userEntity = userRepository.findByUserId(UserId);
        userMaper.updateUser(userDTO, userEntity);

        var roles = rolesRepository.findAllById(userDTO.getRoles());
        userEntity.setRoles(new HashSet<>(roles));

        UserEntity updatedEntity = userRepository.save(userEntity);
        return userMaper.toUserResponse(updatedEntity);
    }
//    Hàm đổi mật khẩu
    public UserResponse changePassword(String UserId, ChangePassDTO changePassDTO) {
        UserEntity userEntity = userRepository.findByUserId(UserId);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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
//    Get accout
    public List<UserResponse> findAll() {
        List<UserEntity> getAll = userRepository.findAll();
        List<UserResponse> SavedGetAll = userMaper.toUserResponseList(getAll);
        return SavedGetAll;
    }
//    Get Accout by user name
    public UserResponse findByUserName(String userName) {
        Optional<UserEntity> getUserName = userRepository.findByUsername(userName);
        UserResponse userResponse = userMaper.toUserResponse(getUserName.get());
        return userResponse;
    }
//delete accout
    public void deleteAccout(String UserId) {
        userRepository.deleteById(UserId);
    }

}
