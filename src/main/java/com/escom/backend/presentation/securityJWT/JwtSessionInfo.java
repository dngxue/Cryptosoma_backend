package com.escom.backend.presentation.securityJWT;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtSessionInfo {
    public static UUID getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getDetails() instanceof Map<?, ?> details) {
            Object id = details.get("id");
            if (id instanceof String idStr) {
                try {
                    return UUID.fromString(idStr);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("ID de usuario inválido en el token JWT");
                }
            }
        }
        throw new RuntimeException("No se pudo obtener el ID del usuario desde el token JWT");
    }

    public static String getEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return auth.getName(); // porque usamos el email como subject
        }
        throw new RuntimeException("No se pudo obtener el email del usuario desde el token JWT");
    }

    public static String getRol() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities() != null && !auth.getAuthorities().isEmpty()) {
            return auth.getAuthorities().iterator().next().getAuthority();
        }
        throw new RuntimeException("No se pudo obtener el rol del usuario desde el token JWT");
    }
}
