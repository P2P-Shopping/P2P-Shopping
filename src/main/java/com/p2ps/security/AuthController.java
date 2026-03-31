package com.p2ps.security;

import com.p2ps.security.dto.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import p2ps.security.dto.*;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/api/auth")
// Permitem accesul de la React (Port 5173) - Esențial pentru testare locală!
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 1. Verifici parola și generezi token-ul (cum făceai și până acum)
        if (!"test@magazin.ro".equals(request.getEmail()) && !"123456".equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Eroare: Email sau parolă incorectă!");
        }
        String jwtToken = jwtUtil.generateToken(request.getEmail()); // sau cum ai tu logica

        // 2. Creezi Cookie-ul HttpOnly
        ResponseCookie springCookie = ResponseCookie.from("token", jwtToken)
                .httpOnly(true)   // JS-ul colegei nu are acces la el (te scapă de XSS)
                .secure(false)    // Pune 'false' cât testați pe localhost, va fi 'true' pe HTTPS în producție
                .path("/")        // Cookie-ul e valabil pe toată aplicația
                .maxAge(24 * 60 * 60) // Valabilitate (ex: 1 zi în secunde)
                .build();

        // 3. Îl trimiți în header-ul răspunsului
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body("Login Successful!"); // Acum nu mai trimiți token-ul în body!
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Object registrationData) {
        // Momentan doar simulăm succesul înregistrării
        return ResponseEntity.ok("Înregistrare reușită! Acum te poți loga.");
    }
}