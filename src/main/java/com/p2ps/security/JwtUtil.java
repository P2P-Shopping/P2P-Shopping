package com.p2ps.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Cheia secreta folosita pentru semnarea criptografica la tokens.
    // In mediile de productie, aceasta valoare nu se tine hardcodata, ci se citeste din variabile de mediu
    // Ar trebui schimbat intr-o iteratie viitoare
    private final String SECRET_KEY_STRING = "A_Very_Long_Secret_Key_For_Our_P2P_Shopping_App_Which_Must_Be_At_Least_256_Bits!";
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    // Durata de valabilitate a token-ului in milisecunde (setat implicit la 24 de ore).
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // Genereaza un token JWT pe baza adresei de email a utilizatorului autentificat.
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extrage subiectul (adresa de email in acest context) din payload-ul token-ului.
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Verifica daca data curenta a depasit limita de expirare setata in token.
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Valideaza token-ul verificand corespondenta email-ului extras cu cel din baza de date si valabilitatea temporala.
    public boolean isTokenValid(String token, String userEmailFromDatabase) {
        final String email = extractEmail(token);
        return (email.equals(userEmailFromDatabase) && !isTokenExpired(token));
    }

    // Metoda utilitara interna folosita pentru a parsa token-ul semnat si a extrage datele (claims).
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}