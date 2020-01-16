package com.example.socialnetwork.service;

import com.example.socialnetwork.models.User;
import com.example.socialnetwork.repositories.UserRepo;
import com.example.socialnetwork.validators.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class UserService implements IUserService {

    private final UserRepo repository;

    @Autowired
    public UserService(UserRepo repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(User account)
            throws EmailExistsException {

        if (emailExist(account.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress: "
                            +  account.getEmail());
        }

        User user = new User();
        user.setUsername(account.getUsername());
        user.setPassword(account.getPassword());
        user.setEmail(account.getEmail());
        user.setRoles(Arrays.asList("ROLE_USER"));
        return repository.save(user);

    }
    private boolean emailExist(String email) {
        User user = repository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}
