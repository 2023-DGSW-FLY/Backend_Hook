package com.innosync.hook.token;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.util.Date;

public class JwtTokenUtil {
    private static long expiredTimeMs = 1000 * 60 * 60; // 1시간
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
            Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isRefreshTokenValid(String refreshToken, String key) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(refreshToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String generateAccessTokenFromRefreshToken(String refreshToken, String key) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(refreshToken)
                .getBody();

        String userAccount = claims.get("userAccount", String.class);
        return createAccessToken(userAccount, key);
    }
}
