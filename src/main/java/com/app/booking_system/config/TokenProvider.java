package com.app.booking_system.config;

import com.app.booking_system.entity.UserCredential;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenProvider {

    @Value("${security.jwt.secret-key}")
    private String JWT_SECRET;


    public String generateAccessToken(UserCredential user) {
        try {
            String username = user.getUsername();
            String firstName = user.getFirstName();
            String userId = user.getId();

            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.create()
                    .withSubject(username)
                    .withClaim("UserEmail", username)
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(genAccessExperationDate())
                    .withClaim("FirstName", firstName)
                    .withClaim("UserId", userId)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error while generating access token", exception);
        }
    }

    public String getUserIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm).build().verify(token).getClaim("UserId").asString();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error while validating token", exception);
        }
    }




    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            System.err.println("JWT Verification Exception: " + exception.getMessage());
            throw new JWTVerificationException("Error while validating token", exception);
        }
    }

    public String refreshAccessToken(String refreshToken) {
        String email = validateRefreshToken(refreshToken);
        UserCredential user = new UserCredential(null, email, null, null,null);
        return generateAccessToken(user);
    }

    public String generateRefreshToken(UserCredential user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.create().withSubject(user.getUsername()).withClaim("username", user.getUsername())
                    .withExpiresAt(genAccessExperationDate()).sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error while generating refresh token", exception);
        }
    }

    public String validateRefreshToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm).build().verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error while validating refresh token", exception);
        }
    }

    public Instant genAccessExperationDate() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.UTC);
    }


    public Instant genRefreshExperationDate() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.UTC);

    }

}