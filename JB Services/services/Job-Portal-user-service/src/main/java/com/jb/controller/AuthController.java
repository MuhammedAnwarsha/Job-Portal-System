package com.jb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jb.payload.AuthResponse;
import com.jb.payload.LoginRequest;
import com.jb.payload.SignupRequest;
import com.jb.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  
  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> signup(@RequestBody @Valid SignupRequest req)
      throws Exception {
    return ResponseEntity.ok(authService.signup(req));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest req) throws Exception {
    return ResponseEntity.ok(authService.login(req));
  }
}
