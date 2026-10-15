package com.jb.service;

import com.jb.payload.AuthResponse;
import com.jb.payload.LoginRequest;
import com.jb.payload.SignupRequest;

public interface AuthService {
  
  AuthResponse signup(SignupRequest req) throws Exception;
  
  AuthResponse login(LoginRequest req) throws Exception;

}
