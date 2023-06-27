package com.example.jwtcorrect.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

@Component
public class JWTUtility implements Serializable {
    private final String secretKey
            = "6250655368566D5970337336763979244226452948404D635166546A576E5A72";

    private  int expiry = 5*60*1000;
    public SecretKey getKey(String secretKey)
    {
        byte [] bytearray= HexFormat.of().parseHex(secretKey);
        return Keys.hmacShaKeyFor(bytearray);
    }
    public String generateToken(String username) {
        Map<String, Object> claims= new HashMap<>();
      return   Jwts.builder()
              .setClaims(claims)
              .setSubject(username)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis()+ expiry))

              .signWith(getKey(secretKey)).compact();





    }
//this code is also correct
  /*  public String extractUsername(String jwtToken) {
       return extractall(jwtToken).getSubject();
    }*/
    public  String extractUsername(String jwtToken)
    {
        return extractClaim(jwtToken,Claims::getSubject);
    }
    //instead of T we can write String
    public  <T> T extractClaim(String jwtToken, Function<Claims,T> claimResolver)
    {
        return  claimResolver.apply(extractall(jwtToken));
    }
    public Claims extractall(String jwtToken)
    {
       return Jwts           .parserBuilder()
                .setSigningKey(getKey(secretKey))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

    }
}
