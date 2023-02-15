package com.example.demo2.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import com.example.demo2.entity.User;
import com.example.demo2.model.TokenPayload;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

    private final String secret = "HIEP_DZ";

    public TokenPayload getTokenPayload(String token) {
        return getClaimsFromToken(token, (Claims claims) ->{
            Map<String, Object> mapResult = (Map<String,Object>) claims.get("payload");
            return TokenPayload.builder().userId((int) mapResult.get("userId"))
                    .email((String) mapResult.get("email")).build();
        });
    }

    private <T> T getClaimsFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public String generateToken(User user, Long expriredDate) {
        Map<String, Object> claims = new HashMap<>();
        TokenPayload tokenPayload = TokenPayload.builder().userId(user.getId()).email(user.getEmail()).build();
        claims.put("payload", tokenPayload);

        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expriredDate * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }


    public boolean validate(String token, User user) {
        TokenPayload tokenPayload = getTokenPayload(token);
        return tokenPayload.getUserId() == user.getId() && 
                tokenPayload.getEmail().equals(user.getEmail()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiredDate = getClaimsFromToken(token, Claims::getExpiration);
        return expiredDate.before(new Date());
    }
}
