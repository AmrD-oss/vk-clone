package com.example.socialnetwork.service;

import com.example.socialnetwork.models.Role;
import com.example.socialnetwork.models.UserEntity;
import com.example.socialnetwork.repositories.RoleRepo;
import com.example.socialnetwork.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final EntityManager entityManager;
    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(EntityManager entityManager, UserRepo userRepository, RoleRepo roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity == null) {
            throw new UsernameNotFoundException("Пользователя с таким логином не существует!");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(Role role : userEntity.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(), grantedAuthorities);
    }


    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("Пользователя с id: " + userId + " не существует"));
    }


    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }


    public boolean saveUser(UserEntity userEntity) {
        UserEntity userEntityFromDB = userRepository.findByUsername(userEntity.getUsername());

        if(userEntityFromDB != null) {
            return false;
        }

        userEntity.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userRepository.save(userEntity);

        return true;
    }


    public boolean deleteUser(Long userId) {
        if(userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);

            return true;
        }

        return false;
    }
}
