package com.securityGear.app.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private String secretKey = "";

    public JwtService(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


//  to get our token we pass a username and receive a token
    public String generateToken(String username) {

        Map<String,Object> claims = new HashMap<>();

        return Jwts.builder().claims().add(claims).subject(username).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + (60 * 60 * 30 ))).and().signWith(getKey()).compact();
    }


//  getter for a key
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


//  to extract a username from a token
    public String extractUserName(String token) {
//      extract username from token
        return extractClaim(token,Claims::getSubject);
    }


//    used by the extractUserName function
//  -------------------------------------------------------------------------------- //

//  responsible for parsing the claims from tokens
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

//  -------------------------------------------------------------------------------- //


//  for validate the token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


//  we want to check if the date is expired for the token
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

//  get the expiration time from a token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
