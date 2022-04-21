package com.lionfish.robo_clipping_kindle.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

public class JWTUtil {

    private static final String JWT_PASSWORD = "hidden-secret-password-123";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final String ISSUER = "authenticator";
    private static final long MILLI_MULTIPLIER = 1000;

    public static String generateJWT(String user, String subject, long expTimeInSeconds){
        long now = System.currentTimeMillis();
        Date nowDate = new Date(now);
        HashMap<String, Object> header = new HashMap<>();
        header.put("alg", SIGNATURE_ALGORITHM.name());
        header.put("typ", "JWT");
        JwtBuilder builder = Jwts.builder()
                .setHeader(header)
                .setId(user)
                .setIssuedAt(nowDate)
                .setSubject(subject)
                .setIssuer(ISSUER)
                .signWith(SIGNATURE_ALGORITHM, JWT_PASSWORD);

        if(expTimeInSeconds > 0){
            long expiration = now + (expTimeInSeconds * MILLI_MULTIPLIER);
            builder.setExpiration(new Date(expiration));
        }

        return builder.compact();
    }

    public static boolean validateToken(String token){
        try{
            Claims claims = Jwts.parser().setSigningKey(JWT_PASSWORD).parseClaimsJws(token).getBody();
            System.out.println(claims);
            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
}
