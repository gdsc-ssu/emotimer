package com.gdsc.timerservice.oauth.token;

import com.gdsc.timerservice.oauth.entity.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class TokenTest {
    public static void main(String[] args) {
//        Claims claims = Jwts.claims().setSubject("yeonnex@gmail.com");
//        claims.put("id", 1);
//        claims.put("role", RoleType.USER);
//
//        String jwtToken = Jwts.builder()
//                .setClaims(claims)
//                .signWith(Keys.hmacShaKeyFor("hello123hello123hello123hello123hello123".getBytes()), SignatureAlgorithm.HS256)
//                .setExpiration(new Date())
//                .compact();

        Claims claims = Jwts.claims().setSubject("12".toString());
        String my = Jwts.builder()
                .setSubject("yeonnex@gmail.com")
                .setClaims(claims)
                .signWith(Keys.hmacShaKeyFor("hello123hello123hello123hello123hello123".getBytes()), SignatureAlgorithm.HS256)
                .setExpiration(new Date())
                .compact();
        System.out.println(my);
    }
}
