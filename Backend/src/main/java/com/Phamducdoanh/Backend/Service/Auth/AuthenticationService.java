package com.Phamducdoanh.Backend.Service.Auth;

import com.Phamducdoanh.Backend.DTO.Request.AuthenticationRequestDTO;
import com.Phamducdoanh.Backend.DTO.Request.IntroSpectRequestDTO;
import com.Phamducdoanh.Backend.DTO.Response.AuthenticationResponseDTO;
import com.Phamducdoanh.Backend.DTO.Response.IntroSpectResponseDTO;
import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
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

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {
    UserRepository userResponse;

    @NonFinal
    //        Tạo header(thuật toán) của JWT
    @Value("${jwt.siginer-key}")
    protected String SIGNER_KEY;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO requestDTO){
//        get thông tin user
        var user = userResponse.findByUsername(requestDTO.getUsername())
                .orElseThrow(() ->new AppExeption(ErrorCode.USER_NOT_EXISTED));

//        Kiểm tra pass được nhập vào và pass trong db
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(requestDTO.getPassword(), user.getPassword());

        if(!authenticated)
            throw new AppExeption(ErrorCode.UNAUTHENTICATED);

        var token = genarateToken(requestDTO.getUsername());

        return AuthenticationResponseDTO.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

//    Hàm tạo token
    private String genarateToken(String username){
        // key mã hóa làm secret key
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

//        Tạo các thuộc tính payload của JWT
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)//Đại diện cho user đăng nhập
                .issuer("Phamducdoanh.com")// Xác định token được issuer từ ai
                .issueTime(new Date())//xác định thời gian tạo issue
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()// hết hạn sau 1 giờ
                ))//xác định thời hạn token
                .claim("customClaim", "Custom")//thêm các claim khác
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
}
