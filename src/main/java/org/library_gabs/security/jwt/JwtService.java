package org.library_gabs.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.library_gabs.LibraryApp;
import org.library_gabs.domain.entity.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    private String expiration = "30";

    private String keySignature = "VW0gZ2F0byBwb2RlIGVzdGFyIHNpbXVsdGFuZWF" +
            "tZW50ZSB2aXZvIGUgbW9ydG8gYXTDqSBxdWUgc2VqYSBvYnNlcnZhZG8u";
    public Claims obtainClaims (String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(keySignature)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValid (String token) {
        try {
            Claims claims = obtainClaims(token);
            Date dateExpiration = claims.getExpiration();
            LocalDateTime dateTime = dateExpiration
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            return !LocalDateTime.now().isAfter(dateTime);
        } catch (Exception e) {
            return false;
        }
    }

    public String obtainLoginUser (String token) throws ExpiredJwtException {
        return (String) obtainClaims(token).getSubject();
    }

    public String generateToken (Users users) {
        try {
            long expString = Long.parseLong(expiration);
            LocalDateTime dateHourExpiration = LocalDateTime.now().plusMinutes(expString);
            Date date = Date.from(dateHourExpiration.atZone(ZoneId.systemDefault()).toInstant());
            return Jwts.builder()
                    .setSubject(users.getLogin())
                    .setExpiration(date)
                    .signWith(SignatureAlgorithm.HS512, keySignature)
                    .compact();
        } catch (NumberFormatException e) {
            throw new RuntimeException("Erro ao converter a expiração para número.", e);
        }
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext contexto = SpringApplication.run(LibraryApp.class);
        JwtService service = contexto.getBean(JwtService.class);
        Users usuario = Users.builder().login("Maria").build();
        String token = service.generateToken(usuario);
        System.out.println(token);

        boolean isTokenValido = service.tokenValid(token);
        System.out.println("O token está válido? " + isTokenValido);

        System.out.println(service.obtainLoginUser(token));
    }
}

//    JwtService jwtService = new JwtService();
//        Users user = new Users("Gabs", "1234");
//
//        String token = jwtService.generateToken(user);
//        System.out.println("Token gerado: " + token);
