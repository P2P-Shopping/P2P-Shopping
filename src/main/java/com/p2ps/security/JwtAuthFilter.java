package com.p2ps.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.http.Cookie;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // Injectam clasa JwtUtil ca sa o folosim la validare
    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        String userEmail = null; // Corectat: am redenumit din 'username' in 'userEmail'

        // NOU: Căutăm token-ul în Cookie-uri în loc de Header
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // Dacă am găsit token-ul, extragem email-ul din el
        if (token != null) {
            userEmail = jwtUtil.extractEmail(token); // Extragem in 'userEmail'
        }

        // Verificam daca userul nu este deja autentificat in contextul de securitate
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Momentan nu avem clasa User din baza de date.
            // Asa ca validam doar daca token-ul nu e expirat.
            if (!jwtUtil.isTokenExpired(token)) { // Corectat: am folosit 'token' in loc de 'jwt'

                // Cream un obiect de autentificare standard pentru Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userEmail, null, new ArrayList<>()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Setam userul ca fiind logat in contextul curent
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Lasam request-ul sa mearga mai departe
        filterChain.doFilter(request, response);
    }
}