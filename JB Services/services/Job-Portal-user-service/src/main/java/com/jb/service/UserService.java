package com.jb.service;

import java.util.List;
import com.jb.dto.response.UserResponse;
import com.jb.model.Users;
import com.jb.payload.UpdateUserRequest;

public interface UserService {

  Users getUserByEmail(String email) throws Exception;

  Users getUserById(Long id) throws Exception;

  List<Users> getAllUsers();

  UserResponse updateProfile(String email, UpdateUserRequest req) throws Exception;

  /**
   * Below are the ADMIN action functions
   * 
   * @throws Exception
   */

  UserResponse suspendUser(Long id) throws Exception;

  UserResponse activateUser(Long id) throws Exception;

  UserResponse deleteUser(Long id) throws Exception;
}
