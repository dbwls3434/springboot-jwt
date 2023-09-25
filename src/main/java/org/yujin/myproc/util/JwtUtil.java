package org.yujin.myproc.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    //token 무력화
    public static void invalidate(String token, String secretKey) {
         Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody()
                .setExpiration(new Date());
                //.getExpiration().
                //before(new Date());
    }

    //token 안에 들어 있는 email 정보 꺼내오기
    public static String getEmail(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("email", String.class);
    }

    //token 만료 여부 확인
    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    //token 생성
    public static String createToken(String email, String secretKey, long expireTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
