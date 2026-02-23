	package com.app.UserService.controller;
	
	import java.util.Set;
	import java.util.stream.Collectors;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.security.authentication.AuthenticationManager;
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;
	
	import com.app.UserService.dto.AuthRequest;
	import com.app.UserService.dto.AuthResponse;
	import com.app.UserService.entity.User;
	import com.app.UserService.service.CustomUserServiceImpl;
	import com.app.UserService.service.UserServiceImpl;
	import com.app.UserService.util.JwtUtil;
	
	@RestController
	@RequestMapping("/auth")
	public class AuthController {
	
	    @Autowired
	    private AuthenticationManager authManager;
	 
	
	
	    @Autowired
	    private CustomUserServiceImpl userService;
	    
	    @Autowired
	    UserServiceImpl userServiceImpl;
	
	    @Autowired
	    private JwtUtil jwtUtil;
	    
	    @GetMapping
	    public String message() {
	    	return "Hello from mahi";
	    	
	    }
	    @PostMapping("/register")
	    public String register(@RequestBody User user) {
	
	      
	
	    	userServiceImpl.registerUser(user);
	
	        return "User registered successfully";
	    }    
	    
	    
	    
	
	    @PostMapping("/login")
	    public AuthResponse login(@RequestBody AuthRequest request) {
	
	        try {
	            authManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                    request.getUsername(),
	                    request.getPassword()
	                )
	            );
	        } catch (Exception e) {
	            System.out.println("Authentication failed: " + e.getMessage());
	            throw e;
	        }
	
	        UserDetails userDetails =
	                userService.loadUserByUsername(request.getUsername());
	
	        Set<String> roles = userDetails.getAuthorities()
	                .stream()
	                .map(a -> a.getAuthority())
	                .collect(Collectors.toSet());
	
	        String token =
	                jwtUtil.generateToken(userDetails.getUsername(), roles);
	
	        return new AuthResponse(token);
	    }
	}