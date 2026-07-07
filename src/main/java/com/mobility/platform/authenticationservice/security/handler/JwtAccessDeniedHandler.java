package com.mobility.platform.authenticationservice.security.handler;

import com.mobility.platform.authenticationservice.dto.response.ApiErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(@NonNull HttpServletRequest request,
                       @NonNull HttpServletResponse response,
                       @NonNull AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        ApiErrorResponse  apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpServletResponse.SC_FORBIDDEN)
                .error("Forbidden")
                .message(accessDeniedException.getMessage())
                .path(request.getRequestURI())
                .build();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), apiErrorResponse);
    }
}
