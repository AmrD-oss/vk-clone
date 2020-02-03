package com.example.socialnetwork.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
