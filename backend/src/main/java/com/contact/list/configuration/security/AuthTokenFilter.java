package com.contact.list.configuration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Optional;

public class AuthTokenFilter extends OncePerRequestFilter {

  @Value("${app.jwtSecret}")
  private String jwtSecret;

  @Autowired
  private AuthComponent authComponent;

  @Autowired
  private JwtTokenHandler jwtHandler;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      var jwtOpt = parseJwt(request);

      if (jwtOpt.isEmpty()) {
        filterChain.doFilter(request, response);
        return;
      }

      var jwt = jwtOpt.get();
      if (jwtHandler.validateJwtToken(jwt)) {
        String username = jwtHandler.getUserNameFromJwtToken(jwt);

        UserDetails userDetails = authComponent.loadUserByUsername(username);
        
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails,null, new ArrayList<>());
        
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e.getMessage());
    }

    filterChain.doFilter(request, response);
  }

  private Optional<String> parseJwt(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
            .map(authorization -> authorization.replace("Bearer ", ""));
  }
}
