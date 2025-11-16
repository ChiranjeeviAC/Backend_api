package com.shreejenu.service;

import com.shreejenu.dto.AuthRequest;
import com.shreejenu.dto.RegisterRequest;
import com.shreejenu.entity.User;
import com.shreejenu.repository.UserRepository;
import com.shreejenu.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService; // This is our UserDetailsServiceImpl

    @Autowired
    private JwtService jwtService;

    @Override
    public void register(RegisterRequest registerRequest) {
        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole("ROLE_USER"); // Assign default role

        userRepository.save(newUser);
    }

    @Override
    public String login(AuthRequest authRequest) {
        // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        // If authentication is successful, load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

        // Generate and return the JWT
        return jwtService.generateToken(userDetails);
    }
}