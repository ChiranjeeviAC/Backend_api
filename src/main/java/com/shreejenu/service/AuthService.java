package com.shreejenu.service;

import com.shreejenu.dto.AuthRequest;
import com.shreejenu.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest registerRequest);
    String login(AuthRequest authRequest);
}