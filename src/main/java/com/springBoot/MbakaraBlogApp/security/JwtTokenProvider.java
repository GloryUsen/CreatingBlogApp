// package com.springBoot.MbakaraBlogApp.security;

// import java.security.Key;
// import java.util.Date;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpStatus;
// import org.springframework.security.core.Authentication;
// import org.springframework.stereotype.Component;

// import com.springBoot.MbakaraBlogApp.exception.BlogCommentPostException;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.MalformedJwtException;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.UnsupportedJwtException;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;

// @Component
// public class JwtTokenProvider {

//     @Value("${app.jwt-secret}")
//     private String jwtSecret;

//     @Value("${app.jwt-expiration-milliseconds}")
//     private long jwtExpirationDate;

//     // Generating JWT Token
//     public String generateToken(Authentication authentication){
//         String username = authentication.getName();
//         //String username = authentication.getConsumersEmail().


//         //  Adding roles into JWT
//         var authorities = authentication.getAuthorities()
//         .stream()
//         .map(auth -> auth.getAuthority())
//         .toList();

//         // Creating Claims
//         Claims claims = Jwts.claims().setSubject(username);
//         claims.put("roles", authorities);

//         Date currentDate = new Date();
//         Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);


// //        String token = Jwts.builder()
// //                .setSubject(username)
// //                .setIssuedAt(new Date())
// //                .setExpiration(expireDate)
// //                .signWith(Key())
// //                .compact();
// //        return token;

//         return Jwts.builder()
//                 .setSubject(username)
//                 .setIssuedAt(currentDate)
//                 .setExpiration(expireDate)
//                 .signWith(getKey(), SignatureAlgorithm.HS512)
//                 .compact();

//     }

//     private Key getKey(){
//         //return Keys.hmacShaKeyFor(jwtSecret.getBytes());
//       return Keys.hmacShaKeyFor(jwtSecret.getBytes());
//       }


//     //Getting Username from JWT Token Or Extract username from token
//     public String getUsername(String token){
//         Claims claims = Jwts.parserBuilder()
//                 .setSigningKey(getKey())
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();
// //        String username = claims.getSubject();
//         return claims.getSubject();
//     }

//     // Validating JWT Token
//     public Boolean validateToken(String token){
//         try {
//             Jwts.parserBuilder()
//                     .setSigningKey(getKey())
//                     .build()
//                     .parseClaimsJws(token);
//             return true;

//         } catch (MalformedJwtException ex) {
//             throw new BlogCommentPostException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
//         }catch (ExpiredJwtException ex){
//             throw new BlogCommentPostException(HttpStatus.BAD_REQUEST, "Expired JWT token");

//         }catch (UnsupportedJwtException ex){
//             throw new BlogCommentPostException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");

//         }catch (IllegalArgumentException ex){
//             throw new BlogCommentPostException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
//         }
//     }
// }


// The above commented code failed because JwtTokenProvider throwed an exception during bean creation.
// The root cause was that jwtsecret being missing or too short for HS512, because i was using ".signWith(getKey(), SignatureAlgorithm.HS512)"
// For HS512, JJWT requires a secret key ≥ 64 bytes.
// So spring found a shorter key or no key, then throws an error called, IllegalArgumentException.




package com.springBoot.MbakaraBlogApp.security;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springBoot.MbakaraBlogApp.exception.BlogCommentPostException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .toList();

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BlogCommentPostException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new BlogCommentPostException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        }
    }
}
