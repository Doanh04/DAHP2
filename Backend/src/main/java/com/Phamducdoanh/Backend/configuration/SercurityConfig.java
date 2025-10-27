package com.Phamducdoanh.Backend.configuration;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
@EnableMethodSecurity(prePostEnabled = true)
// Xử lý các enpoin được phép truy cập local và các enpoin dùng token
public class SercurityConfig {

    final String[] PUBLIC_ENDPOINTS_JWT = {"/auth/token", "/auth/introspect","/auth/createuser"
                ,};
    final String[] PUBLIC_ENPOINTS_PRODUCTS = {"/dashboard/product/productall",
            "/dashboard/product/categoryid-product/{categoryId}","/dashboard/product/getname"
            ,"/dashboard/product/filterproduct"};
    final String[] PUBLIC_ENDPOINTS_IMAGE = {"/product/images/{productId}"};
    @Value("${jwt.siginer-key}")
    String siginerKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(requests ->
                requests.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS_JWT).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENPOINTS_PRODUCTS).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS_IMAGE).permitAll()
                        .anyRequest().authenticated());//local enpoin;

        // nhận token lấy quyền theo role
        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(jwtDecoder())
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        // Trả ra lỗi nếu sai hoặc token thiếu quyền
                        .authenticationEntryPoint(new JwtEntryPoint())
                        .accessDeniedHandler(new JwtAccessDeniedHandler())
        );

        // Vô hiệu hóa CSRF
        return httpSecurity.build();
    }
    //Cấu hình CORS global
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

//    Hàm thực hiện decode token có hợp lệ không
    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(siginerKey.getBytes(), "HS512");

        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
//    Lấy Authoroties từ JWT
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        //Chỉ định claim chưa quyền theo JWT
        grantedAuthoritiesConverter.setAuthoritiesClaimName("scope");
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        // Tách chuổi scope
        grantedAuthoritiesConverter.setAuthoritiesClaimDelimiter(",");

        //trả về jwtAuthenticationConverter
        JwtAuthenticationConverter jwtconverter = new JwtAuthenticationConverter();
        jwtconverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtconverter;
    }

}
