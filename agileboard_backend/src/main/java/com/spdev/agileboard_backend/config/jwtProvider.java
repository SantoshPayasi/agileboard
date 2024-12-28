package com.spdev.agileboard_backend.config;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


public class jwtProvider {
     static SecretKey key = Keys.hmacShaKeyFor(jwtConstants.JWT_SECRET_KEY.getBytes());

   public static String generateToken(UsernamePasswordAuthenticationToken authentication){
     String jwt = Jwts.builder().setIssuedAt(new Date())
     .setExpiration(new Date(new Date().getTime() + 24*60*60*1000))
     .claim("email", authentication.getName())
     .signWith(key)
     .compact();
   
     return jwt;
   }


   public static String getEmailfromToken(String token) throws Exception{
    try {
        if(token==null){
            throw new Exception("Token Not Found Exception");
         }
    
          Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
          String email = String.valueOf(claims.get("email"));
          return email;
    } catch (Exception e) {
       throw new InternalAuthenticationServiceException(e.getMessage());
    }
    
   }
}
