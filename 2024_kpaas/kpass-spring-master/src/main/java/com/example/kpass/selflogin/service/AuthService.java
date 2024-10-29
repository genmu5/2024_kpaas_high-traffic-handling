package com.example.kpass.selflogin.service;


import com.example.kpass.selflogin.entity.UserEntity;
import com.example.kpass.selflogin.repository.UserRepository;
import com.example.kpass.selflogin.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public String registerUser(String email, String password, String passwordConfirm, String name) {
        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.findByUserEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        UserEntity newUser = new UserEntity();
        newUser.setUserName(name);
        newUser.setUserEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setUserRole("ROLE_USER");
        newUser.setUserLoginType("local");
        userRepository.save(newUser);

        return "User registered successfully";
    }

    public String loginUser(String email, String password) {
        UserEntity user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtProvider.createToken(user.getUserEmail());
    }
}

