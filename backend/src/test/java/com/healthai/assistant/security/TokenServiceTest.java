package com.healthai.assistant.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.healthai.assistant.web.ApiException;
import org.junit.jupiter.api.Test;

class TokenServiceTest {
    private final TokenService tokenService = new TokenService(
            "test-secret-that-is-long-enough-for-the-test", 3600);

    @Test
    void roundTripsUserId() {
        assertEquals(42L, tokenService.readUserId(tokenService.createToken(42)));
    }

    @Test
    void rejectsTamperedToken() {
        String token = tokenService.createToken(42);
        String[] parts = token.split("\\.");
        String alteredSignature = (parts[1].charAt(0) == 'A' ? "B" : "A") + parts[1].substring(1);
        String tampered = parts[0] + "." + alteredSignature;
        assertThrows(ApiException.class, () -> tokenService.readUserId(tampered));
    }
}
