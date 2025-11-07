package com.Phamducdoanh.Backend.Service.Auth;

import com.Phamducdoanh.Backend.DTO.Request.AuthenticationRequestDTO;
import com.Phamducdoanh.Backend.DTO.Request.IntroSpectRequestDTO;
import com.Phamducdoanh.Backend.DTO.Request.UserDTO;
import com.Phamducdoanh.Backend.DTO.Response.AuthenticationResponseDTO;
import com.Phamducdoanh.Backend.DTO.Response.IntroSpectResponseDTO;
import com.Phamducdoanh.Backend.DTO.Response.UserResponse;
import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Maper.UserMaper;
import com.Phamducdoanh.Backend.Repository.RolesRepository;
import com.Phamducdoanh.Backend.Repository.UserRepository;
import com.Phamducdoanh.Backend.entity.CartEntity;
import com.Phamducdoanh.Backend.entity.RolesEntity;
import com.Phamducdoanh.Backend.entity.UserEntity;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMaper userMaper;
    private final EntityManager  entityManager;
    private final RolesRepository rolesRepository;

    @NonFinal
    //        Tạo header(thuật toán) của JWT
    @Value("${jwt.siginer-key}")
    protected String SIGNER_KEY;
    @Transactional(readOnly = true)
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO requestDTO){
//        get thông tin user
        var user = userRepository.findByUsernameWithRolesAndPermissions(requestDTO.getUsername())
                .orElseThrow(() ->new AppExeption(ErrorCode.USER_NOT_EXISTED));

        entityManager.refresh(user);

//        Kiểm tra pass được nhập vào và pass trong db
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(requestDTO.getPassword(), user.getPassword());

        if(!authenticated)
            throw new AppExeption(ErrorCode.UNAUTHENTICATED);

        var token = genarateToken(user);

        return AuthenticationResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .fullName(user.getName())
                .authenticated(true)
                .build();
    }

//    Hàm tạo token
    private String genarateToken(UserEntity userEntity){
        // key mã hóa làm secret key
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

//        Tạo các thuộc tính payload của JWT
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userEntity.getUsername())//Đại diện cho user đăng nhập
                .issuer("Phamducdoanh.com")// Xác định token được issuer từ ai
                .issueTime(new Date())//xác định thời gian tạo issue
                .expirationTime(new Date(
                        Instant.now().plus(30, ChronoUnit.DAYS).toEpochMilli()// hết hạn sau 30 ngày
                ))//xác định thời hạn token
                .claim("scope", buildScope(userEntity))//thêm các claim khác
                .claim("userId", userEntity.getUserId())
                .claim("name", userEntity.getName())
                .claim("username", userEntity.getUsername())
                .build();
    // tạo payload
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
//        Trả về JWT
        JWSObject jwsObject =new JWSObject(header,payload);
        // ký token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("cannot create token",e);
            throw new RuntimeException(e);
        }
    }

//    Hàm build scope
private String buildScope(UserEntity userEntity){
    StringJoiner stringJoiner = new StringJoiner(",");

    if(!CollectionUtils.isEmpty(userEntity.getRoles()))
        userEntity.getRoles().forEach(rolesEntity -> {
            stringJoiner.add("ROLE_"+rolesEntity.getRoleName());

            if(!CollectionUtils.isEmpty(rolesEntity.getPermissions()))
                rolesEntity.getPermissions().forEach(permissionEntity ->
                        stringJoiner.add(permissionEntity.getPermisionName()));
        });

    return stringJoiner.toString();
}

//    Hàm introspect kiểm tra hiệu lực của token
    public IntroSpectResponseDTO introSpect(IntroSpectRequestDTO introSpectRequestDTO)
            throws JOSEException, ParseException {
        var token = introSpectRequestDTO.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expptyTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return  IntroSpectResponseDTO.builder()
                .valid(verified && expptyTime.after(new Date()))
                .build();
    }

    public UserResponse createUser(UserDTO userDTO) {
        if(userRepository.existsByUsername(userDTO.getUsername())){
            throw new AppExeption(ErrorCode.USER_EXISTED);
        }
        UserEntity userEntity = userMaper.toUserDTO(userDTO);


        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        RolesEntity userRole = rolesRepository.findById("USER")
                .orElseThrow(() -> new AppExeption(ErrorCode.NOT_FIND_ROLE));
        Set<RolesEntity> userRoles = new HashSet<>();
        userRoles.add(userRole);
        userEntity.setRoles(userRoles);

        CartEntity cart = CartEntity.builder()
                .createDate(new Date())
                .user(userEntity)
                .build();
        userEntity.setCart(cart);

        UserEntity savedUser = userRepository.save(userEntity);

        return userMaper.toUserResponse(savedUser);
    }
}
