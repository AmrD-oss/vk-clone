package com.example.socialnetwork.service;

import org.springframework.security.core.userdetails.User;

public interface SecurityService {
    User findLoggedInUsername();

    void autoLogin(String username, String password);
}
