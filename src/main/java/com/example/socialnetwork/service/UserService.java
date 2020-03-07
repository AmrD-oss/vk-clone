package com.example.socialnetwork.service;

import com.example.socialnetwork.models.ExchangeRates;
import com.example.socialnetwork.models.Role;
import com.example.socialnetwork.models.UserEntity;
import com.example.socialnetwork.models.Valute;
import com.example.socialnetwork.repositories.RoleRepository;
import com.example.socialnetwork.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Value("${application.cbr-daily-json}")
    private String cbrUrlValute;

    private final RestTemplate restTemplate;
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(RestTemplateBuilder restTemplateBuilder,
                       EntityManager entityManager,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplateBuilder.build();
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity == null) {
            throw new UsernameNotFoundException("Пользователя с логином: " + userEntity.getUsername() + " не существует!");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(Role role : userEntity.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(), grantedAuthorities);
    }

    public UserEntity getAnAuthorizedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = null;

        try {
            currentUser = findUserByUsername(authentication.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentUser;
    }

    public UserEntity findUserById(Long userId) throws NullPointerException {
        return userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("Пользователя с id: " + userId + " не существует"));
    }


    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username);
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

    public void updateUser(UserEntity userEntity) {
        UserEntity currentUser = getAnAuthorizedUser();

        currentUser.setName(userEntity.getName());
        currentUser.setSurname(userEntity.getSurname());
        currentUser.setEmail(userEntity.getEmail());
        currentUser.setStatus(userEntity.getStatus());
        currentUser.setDateOfBirth(userEntity.getDateOfBirth());
        currentUser.setCity(userEntity.getCity());

        userRepository.save(currentUser);
    }

    public boolean deleteUser(Long userId) {
        if(userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public Map<String, Valute> getAllValutes() {
        ExchangeRates exchangeRates = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            exchangeRates = mapper.readValue(getStringDataOnValutes(), ExchangeRates.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Objects.requireNonNull(exchangeRates).getValute();
    }

    private String getStringDataOnValutes() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                cbrUrlValute,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }
}
