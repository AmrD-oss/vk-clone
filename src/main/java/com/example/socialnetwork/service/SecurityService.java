package com.example.socialnetwork.service;

import org.springframework.security.core.userdetails.User;

public interface SecurityService {
    void autoLogin(String username, String password);
}
