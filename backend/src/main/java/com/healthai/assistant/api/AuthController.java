package com.healthai.assistant.api;

import com.healthai.assistant.api.dto.AuthRequest;
import com.healthai.assistant.api.dto.AuthResponse;
import com.healthai.assistant.api.dto.RegisterRequest;
import com.healthai.assistant.security.AuthInterceptor;
import com.healthai.assistant.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public AuthResponse.UserSummary currentUser(HttpServletRequest request) {
        return authService.currentUser((Long) request.getAttribute(AuthInterceptor.USER_ID_ATTRIBUTE));
    }
}
