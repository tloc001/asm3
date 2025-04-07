package com.poly.lab6_java6.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@Log4j2
public class JwtTokenProvider {
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final String jwtSecret = Base64.getEncoder().encodeToString(secretKey.getEncoded());
    private final long jwtExpiration = 36000000;
//    Thoi gian het han cua 1 token

    //setSubject(username): Lưu username vào JWT.
//setExpiration(expiryDate): Thiết lập thời gian hết hạn.
//signWith(secretKey): Ký JWT bằng secret key.
    public String generateToken(String username) {

        log.info("getSecretKey :{}", jwtSecret);
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + jwtExpiration);
            return Jwts.builder()

                    .setSubject(username)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
//                thuaat toan genderate token
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();
        }
//    lay username tu JWT ra
//    lay ra duoc subject truyen vao

        public String extractUsername (String token){
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        }

        public boolean isTokenExpired (String token){
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration().before(new Date());
        }
//  So sánh username trong token với user đăng nhập.
//Kiểm tra token có hết hạn không.
        public boolean validateToken (String token, String username){
            return (username.equals(extractUsername(token)) && !isTokenExpired(token));
        }

    }
