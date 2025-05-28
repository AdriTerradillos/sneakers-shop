package com.gammatech.sneakers.entity;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;

@Component
public class JWTUtils {

    // Debe tener al menos 256 bits (32 caracteres) para HS256
    private static final String SECRET_KEY = "clave-secreta-super-larga-para-HS256!"; // cambiar por una clave segura y secreta

    // 10 horas de expiración
    private static final long EXPIRATION_MILLIS = 1000 * 60 * 60 * 10;

    public String generateToken(UserDetails userDetails) {
        try {
            JWSSigner signer = new MACSigner(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userDetails.getUsername())
                    .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),
                    claimsSet
            );

            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error al firmar el token JWT", e);
        }
    }

    public String extractUsername(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            return jwt.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new RuntimeException("Error al leer el token JWT", e);
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            boolean signatureValid = jwt.verify(verifier);
            boolean usernameMatches = userDetails.getUsername().equals(jwt.getJWTClaimsSet().getSubject());
            boolean notExpired = new Date().before(jwt.getJWTClaimsSet().getExpirationTime());

            return signatureValid && usernameMatches && notExpired;

        } catch (Exception e) {
            return false;
        }
    }

}
