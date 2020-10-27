package com.pm.ecommerce.account_service.utils;

import com.pm.ecommerce.entities.Account;
import com.pm.ecommerce.entities.Employee;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    public static final long serialVersionUID = -2550185165626007488L;

    private String SECRET_KEY = "sibtain";


    public String generateToken(Account account, String type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",account.getId());
        claims.put("type", type);
        return createToken(claims, account.getEmail());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

}
