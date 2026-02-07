package com.web.bus.dto;

public record AuthResponse(String token, String role, Long userId, String displayName) {
}
