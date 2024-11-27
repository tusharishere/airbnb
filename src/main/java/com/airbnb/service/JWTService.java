package com.airbnb.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiryTime;

    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() throws Exception {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateToken(String username){
     return   JWT.create()
                 .withClaim("name",username)
                 .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                 .withIssuer(issuer)
                 .sign(algorithm);
    }

    public String verifyToken(String token){
        DecodedJWT decodedJwt = JWT.require(algorithm)
                                    .withIssuer(issuer)
                                    .build()
                                    .verify(token);
        return decodedJwt.getClaim("name").asString();
    }
}
