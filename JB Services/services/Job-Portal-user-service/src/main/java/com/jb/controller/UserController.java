package com.jb.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jb.dto.response.UserResponse;
import com.jb.mapper.UserMapper;
import com.jb.model.Users;
import com.jb.payload.UpdateUserRequest;
import com.jb.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/profile")
  public ResponseEntity<UserResponse> getProfile(@RequestHeader("X-User-Email") String email)
      throws Exception {
    Users user = userService.getUserByEmail(email);
    return ResponseEntity.ok(UserMapper.toDto(user));
  }

  @PutMapping("/profile")
  public ResponseEntity<UserResponse> updateProfile(@RequestHeader("X-User-Email") String email,
      @RequestBody UpdateUserRequest req) throws Exception {
    return ResponseEntity.ok(userService.updateProfile(email, req));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) throws Exception {
    Users user = userService.getUserById(userId);
    return ResponseEntity.ok(UserMapper.toDto(user));
  }

  @GetMapping()
  public ResponseEntity<List<UserResponse>> getAllUsers() throws Exception {
    return ResponseEntity.ok(UserMapper.toDtoList(userService.getAllUsers()));
  }

  @PatchMapping("/{userId}/suspend")
  public ResponseEntity<UserResponse> suspendUser(@PathVariable Long userId) throws Exception {
    return ResponseEntity.ok(userService.suspendUser(userId));
  }

  @PatchMapping("/{userId}/activate")
  public ResponseEntity<UserResponse> activateUser(@PathVariable Long userId) throws Exception {
    return ResponseEntity.ok(userService.activateUser(userId));
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<UserResponse> deleteUser(@PathVariable Long userId) throws Exception {
    return ResponseEntity.ok(userService.deleteUser(userId));
  }
}
