package com.ohgiraffers.crud_back.login.service;

import com.ohgiraffers.crud_back.login.model.entity.User;
import com.ohgiraffers.crud_back.login.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createRegularUser(String name, String phoneNumber, String username, String email, String password, PasswordEncoder passwordEncoder) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole("ROLE_USER");
        return userRepository.save(newUser);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}

