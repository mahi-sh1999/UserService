package com.app.UserService.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.UserService.service.CustomUserServiceImpl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	   @Autowired
	    private JwtUtil jwtUtil;

	    @Autowired
	    private CustomUserServiceImpl userDetailsService;

	    @Override
	    protected void doFilterInternal(
	            HttpServletRequest request,
	            HttpServletResponse response,
	            FilterChain filterChain)
	            throws ServletException, IOException, java.io.IOException {

	        String authHeader = request.getHeader("Authorization");

	        if (authHeader != null && authHeader.startsWith("Bearer ")) {

	            String token = authHeader.substring(7);
	            String username = jwtUtil.extractUserName(token);

	            if (username != null &&
	                SecurityContextHolder.getContext().getAuthentication() == null) {

	                UserDetails userDetails =
	                        userDetailsService.loadUserByUsername(username);

	                if (jwtUtil.validateToken(token, userDetails.getUsername())) {

	                    UsernamePasswordAuthenticationToken authToken =
	                            new UsernamePasswordAuthenticationToken(
	                                    userDetails,
	                                    null,
	                                    userDetails.getAuthorities());

	                    SecurityContextHolder.getContext()
	                            .setAuthentication(authToken);
	                }
	            }
	        }

	        filterChain.doFilter(request, response);
	    }
	
}