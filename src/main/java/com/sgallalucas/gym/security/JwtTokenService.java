package com.sgallalucas.gym.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtTokenService {

    @Value(value = "api.security.token.secret")
    private String secret;

    public String generateToken(UserDetailsImpl user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("gym-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
                    .sign(algorithm);
        }
        catch (JWTCreationException ex) {
            throw new RuntimeException("Token creation error");
        }
    }

    public String verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("gym-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException ex) {
            throw new RuntimeException("Token validation error");
        }
    }
}
