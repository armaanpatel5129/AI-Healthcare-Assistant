package com.healthai.assistant.security;

import com.healthai.assistant.web.ApiException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private static final String HMAC_SHA256 = "HmacSHA256";
    private final byte[] secret;
    private final long expirationSeconds;

    public TokenService(@Value("${app.jwt.secret}") String secret,
                        @Value("${app.jwt.expiration-seconds}") long expirationSeconds) {
        if (secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 bytes long.");
        }
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.expirationSeconds = expirationSeconds;
    }

    public String createToken(long userId) {
        long expiresAt = System.currentTimeMillis() / 1000 + expirationSeconds;
        String payload = userId + ":" + expiresAt;
        String encodedPayload = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        return encodedPayload + "." + sign(encodedPayload);
    }

    public long readUserId(String token) {
        try {
            String[] parts = token.split("\\.", -1);
            if (parts.length != 2 || !MessageDigest.isEqual(
                    parts[1].getBytes(StandardCharsets.UTF_8),
                    sign(parts[0]).getBytes(StandardCharsets.UTF_8))) {
                throw new ApiException(401, "Invalid or expired authentication token.");
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
            String[] claims = payload.split(":", -1);
            if (claims.length != 2) {
                throw new ApiException(401, "Invalid or expired authentication token.");
            }
            long userId = Long.parseLong(claims[0]);
            long expiresAt = Long.parseLong(claims[1]);
            if (userId < 1 || expiresAt <= System.currentTimeMillis() / 1000) {
                throw new ApiException(401, "Invalid or expired authentication token.");
            }
            return userId;
        } catch (ApiException exception) {
            throw exception;
        } catch (RuntimeException exception) {
            throw new ApiException(401, "Invalid or expired authentication token.");
        }
    }

    private String sign(String value) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(new SecretKeySpec(secret, HMAC_SHA256));
            return Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (java.security.GeneralSecurityException exception) {
            throw new IllegalStateException("Unable to create authentication token.", exception);
        }
    }
}
