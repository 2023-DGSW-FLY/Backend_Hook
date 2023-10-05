package com.innosync.hook.token;


import com.innosync.hook.req.Response;
import io.jsonwebtoken.*;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.util.Date;

public class JwtTokenUtil {
    private static final long expiredTimeMs = 1000 * 60 * 60; // 1시간
    // private static long expiredTimeMs = 60; // 1분

    public static String createAccessToken(String userAccount, String key) {
        Claims claims = Jwts.claims();
        claims.put("userAccount", userAccount);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public static String createRefreshToken(String userAccount, String key) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofDays(30).toMillis()); //30일
        return Jwts.builder()
                .claim("userAccount", userAccount)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public static boolean isAccessTokenValid(String accessToken, String key) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isRefreshTokenValid(String refreshToken, String key) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key).build()
                    .parseClaimsJws(refreshToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String generateAccessTokenFromRefreshToken(String refreshToken, String key) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key).build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            String userAccount = claims.get("userAccount", String.class);
            return createAccessToken(userAccount, key);
        } catch (ExpiredJwtException e) {
            // 만료된 리프레시 토큰일 경우
            ResponseEntity.status(403);
            return "Expired refresh token"; // 만료된 리프레시 토큰일 경우 오류 메시지 반환
        } catch (JwtException e) {
            // 리프레시 토큰이 잘못된 경우
            ResponseEntity.status(403);
            return "Invalid refresh token"; // 잘못된 리프레시 토큰일 경우 오류 메시지 반환
        }
    }



}
