package com.shreejenu.service;

import com.shreejenu.entity.User;
import com.shreejenu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(String email, String password) {
        // Additional logic like checking if email exists can be added here
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole("ROLE_USER"); // Default role
        return userRepository.save(newUser);
    }
}