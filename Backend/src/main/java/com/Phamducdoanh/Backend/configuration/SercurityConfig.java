package com.Phamducdoanh.Backend.configuration;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
// Xử lý các enpoin được phép truy cập local và các enpoin dùng token
public class SercurityConfig {

    final String[] PUBLIC_ENDPOINTS_JWT = {"/auth/token", "/auth/introspect"
                ,};
    final String[] PUBLIC_ENPOINTS_ACCOUT = {"/dashboard/user/createuser"};
    final String[] PUBLIC_ENPOINTS_PRODUCTS = {"/dashboard/product/productall",
            "/dashboard/product/categoryid-product/{categoryId}","/dashboard/product/getname"};

    @Value("${jwt.siginer-key}")
    String siginerKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(requests ->
                requests.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS_JWT).permitAll()
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENPOINTS_ACCOUT).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENPOINTS_PRODUCTS).permitAll()
                        .anyRequest().authenticated());//local enpoin;

        // nhận token
        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
                );

        // Vô hiệu hóa CSRF
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

//    Hàm thực hiện decode token có hợp lệ không
    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(siginerKey.getBytes(), "HSA512");

        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    };
}
