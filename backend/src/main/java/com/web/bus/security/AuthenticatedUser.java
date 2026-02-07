package com.web.bus.security;

/**
 * Represents the authenticated user extracted from a JWT token.
 * Stored as the principal in the SecurityContext.
 */
public record AuthenticatedUser(Long userId, String subject, String role) {
}
