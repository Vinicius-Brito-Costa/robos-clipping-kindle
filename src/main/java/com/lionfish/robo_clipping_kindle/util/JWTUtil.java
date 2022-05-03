package com.lionfish.robo_clipping_kindle.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;

public class JWTUtil {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);
    private static final String JWT_PASSWORD = "hidden-secret-password-123";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final String ISSUER = "authenticator";
    private static final long MILLI_MULTIPLIER = 1000;

    private JWTUtil(){}

    /**
     * Returns a new JWT token
     * @param user id of the token
     * @param subject purpose of the token
     * @param expTimeInSeconds time until this token is expired
     * @return token
     */
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

    /**
     * Retrieve JWT id
     * @param jwt JWT Token
     * @return JWT id if it was successful, else it will return null
     */
    public static String getUsername(String jwt){
        try{
            return getBody(jwt).getId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Check if token is valid by trying to parse JWT claims
     * @param jwt JWT Token
     * @return true if it was parsed successfully, otherwise will return false
     */
    public static boolean validateToken(String jwt){
        try{
            getBody(jwt);
        }
        catch (Exception e){
            logger.error("[ Error ] Token is invalid.", e);
            return false;
        }
        logger.info("[ Message ] Token is valid");
        return true;
    }

    private static Claims getBody(String jwt){
        return Jwts.parser().setSigningKey(JWT_PASSWORD).parseClaimsJws(jwt.replace("Bearer ", "").strip()).getBody();
    }
}
