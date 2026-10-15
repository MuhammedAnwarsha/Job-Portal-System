package com.jb.service.impl;

import java.time.LocalDateTime;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jb.domain.UserRole;
import com.jb.domain.UserStatus;
import com.jb.mapper.UserMapper;
import com.jb.model.Users;
import com.jb.payload.AuthResponse;
import com.jb.payload.LoginRequest;
import com.jb.payload.SignupRequest;
import com.jb.repository.UserRepository;
import com.jb.security.CustomUserDetailsService;
import com.jb.security.JwtProvider;
import com.jb.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final CustomUserDetailsService customUserDetailsService;
  
  @Override
  public AuthResponse signup(SignupRequest req) throws Exception {

    log.info("Signup attempt for user: {}", req.getFullName());

    if(userRepository.existsByEmail(req.getEmail())) {
      log.warn("Signup failed: email is already registered");
      throw new Exception("Email already registered: " + req.getEmail());
    }
    
    if(req.getRole() == UserRole.ROLE_ADMIN) {
      log.warn("Signup rejected: self-registration as ADMIN is not allowed");
      throw new Exception("Cannot self-register as ADMIN");
    }
    
    Users user = Users.builder()
        .fullName(req.getFullName())
        .email(req.getEmail())
        .password(passwordEncoder.encode(req.getPassword()))
        .role(req.getRole())
        .phone(req.getPhone())
        .lastLogin(LocalDateTime.now())
        .status(UserStatus.ACTIVE)
        .build();
    
    Users savedUser = userRepository.save(user);
    
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

    AuthResponse res = new AuthResponse();
    res.setTitle("Welcome "+savedUser.getFullName());
    res.setMessage("Signup successful");
    res.setJwt(jwt);
    res.setUser(UserMapper.toDto(savedUser));

    log.info("Signup completed successfully for user: {}",savedUser.getFullName());

    return res;
  }

  @Override
  public AuthResponse login(LoginRequest req) throws Exception {
    
    log.info("Login attempt received");

    Authentication authentication = authenticate(
        req.getEmail(),req.getPassword()
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    Users user = userRepository.findByEmail(req.getEmail());
    String jwt = jwtProvider.generateToken(authentication, user.getId());

    user.setLastLogin(LocalDateTime.now());
    userRepository.save(user);

    AuthResponse res = new AuthResponse();
    res.setTitle("Welcome back " + user.getFullName());
    res.setMessage("Login successful");
    res.setJwt(jwt);
    res.setUser(UserMapper.toDto(user));

    log.info("Login completed successfully for userId: {}", user.getId());

    return res;
  }

  private Authentication authenticate(String email, String password) throws Exception {

    UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

    if (userDetails == null) {
      throw new Exception("User not found with email " + email);
    }

    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new Exception("Invalid Password");
    }
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

}
