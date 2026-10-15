package com.jb.security;

import java.io.IOException;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenValidator extends OncePerRequestFilter {

  private final SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String header = request.getHeader(JwtConstants.JWT_HEADER);

    if (header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String jwt = header.substring(7).trim();

    if (jwt.isEmpty()) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing JWT token");
      return;
    }

    try {
      Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

      String email = claims.get("email", String.class);
      String authorities = claims.get("authorities", String.class);

      if (email == null || email.isBlank()) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT claims");
        return;
      }

      List<GrantedAuthority> authorityList = AuthorityUtils
          .commaSeparatedStringToAuthorityList(authorities == null ? "" : authorities);

      Authentication authentication =
          new UsernamePasswordAuthenticationToken(email, null, authorityList);

      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (JwtException | IllegalArgumentException e) {
      SecurityContextHolder.clearContext();
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token");
      return;
    }

    filterChain.doFilter(request, response);
    }
}
