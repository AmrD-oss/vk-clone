package com.example.socialnetwork.service;

import com.example.socialnetwork.models.User;
import com.example.socialnetwork.validators.EmailExistsException;

public interface IUserService {
    User registerNewUserAccount(User account)
            throws EmailExistsException;
}
