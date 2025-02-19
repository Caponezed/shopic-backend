package ru.ystu.shopic_backend.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.ystu.shopic_backend.entity.User;
import ru.ystu.shopic_backend.service.JWTService;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    private String secretKey = "";

    public JWTServiceImpl() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sc = keyGenerator.generateKey();
        secretKey = Base64.getEncoder().encodeToString(sc.getEncoded());
    }

    public String generateJWToken(User user) {
        Map<String, Object> claims = new HashMap<String, Object>();

        var now = new Date(System.currentTimeMillis());
        // TODO: increase jwt lifetime later
        var jwtDeathDate = new Date(System.currentTimeMillis() + 1000 * 60 * 10); // 10 min

            return Jwts.builder()
                    .claims()
                    .add(claims)
                    .subject(user.getEmail())
                    .issuedAt(now)
                    .expiration(jwtDeathDate)
                    .and()
                    .signWith(getKey())
                    .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private SecretKey getKey() {
        byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }
}
