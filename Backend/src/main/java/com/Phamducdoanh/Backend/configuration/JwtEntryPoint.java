package com.Phamducdoanh.Backend.configuration;

import com.Phamducdoanh.Backend.DTO.Response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

// exeption 401 (token không hợp lệ)
public class JwtEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // biên dịch exeption sang json
        ErrorResponse  errorResponse = ErrorResponse.builder()
                .code(401)
                .message("Unauthorized" + authException.getMessage())
                .status("error")
                .build();

        // Ghi JSON vào phản hồi
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
