package bsm.speaker.global.utils;

import bsm.speaker.domain.user.entities.RefreshToken;
import bsm.speaker.domain.user.entities.User;
import bsm.speaker.domain.user.repositories.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HexFormat;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${env.jwt.secretKey}")
    private String JWT_SECRET_KEY;
    @Value("${env.jwt.time.token}")
    private long JWT_TOKEN_MAX_TIME;
    @Value("${env.jwt.time.refreshToken}")
    private long JWT_REFRESH_TOKEN_MAX_TIME;

    public String createAccessToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("code", user.getUserCode());
        claims.put("nickname", user.getNickname());
        return createToken(claims, JWT_TOKEN_MAX_TIME);
    }

    public String createRefreshToken(Long userCode) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        String newRandomToken = HexFormat.of().formatHex(randomBytes);

        RefreshToken newRefreshToken = RefreshToken.builder()
                .token(newRandomToken)
                .userCode(userCode)
                .isAvailable(true)
                .build();
        refreshTokenRepository.save(newRefreshToken);

        Claims claims = Jwts.claims();
        claims.put("token", newRandomToken);
        return createToken(claims, JWT_REFRESH_TOKEN_MAX_TIME);
    }

    private String createToken(Claims claims, long time) {
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + time))
                .signWith(Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getRefreshToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("token", String.class);
    }

    public User getUser(String token) {
        Claims claims = extractAllClaims(token);

        return User.builder()
                .userCode(claims.get("code", Long.class))
                .nickname(claims.get("nickname", String.class))
                .build();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
