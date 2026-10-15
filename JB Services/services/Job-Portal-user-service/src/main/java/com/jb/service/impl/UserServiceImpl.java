package com.jb.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.jb.domain.UserStatus;
import com.jb.dto.response.UserResponse;
import com.jb.mapper.UserMapper;
import com.jb.model.Users;
import com.jb.payload.UpdateUserRequest;
import com.jb.repository.UserRepository;
import com.jb.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public Users getUserByEmail(String email) throws Exception {
    log.info("Fetching user by email");

    Users user = userRepository.findByEmail(email);
    if (user == null) {
      log.warn("User not found for the provided email");
      throw new Exception("User not found");
    }

    log.info("User fetched successfully");
    return user;
  }

  @Override
  public Users getUserById(Long id) throws Exception {
    log.info("Fetching user by ID: {}", id);

    Users user = userRepository.findById(id).orElseThrow(() -> {
      log.warn("User not found with ID: {}", id);
      return new Exception("User not found");
    });

    log.info("User fetched successfully with ID: {}", id);
    return user;
  }

  @Override
  public List<Users> getAllUsers() {
    log.info("Fetching all users");

    List<Users> users = userRepository.findAll();
    log.info("Successfully fetched {} users", users.size());
    return users;
  }

  @Override
  public UserResponse updateProfile(String email, UpdateUserRequest req) throws Exception {
    log.info("Updating profile for user");
    Users user = getUserByEmail(email);

    if (req.getFullName() != null) {
      user.setFullName(req.getFullName());
    }
    if (req.getPhone() != null) {
      user.setPhone(req.getPhone());
    }
    if (req.getProfileImage() != null) {
      user.setProfileImage(req.getProfileImage());
    }

    Users updatedUser = userRepository.save(user);

    log.info("Profile updated successfully for user ID: {}", updatedUser.getId());
    return UserMapper.toDto(updatedUser);
  }

  @Override
  public UserResponse suspendUser(Long id) throws Exception {
    log.info("Suspending user with ID: {}", id);

    Users user = getUserById(id);
    user.setStatus(UserStatus.SUSPENDED);
    user.setSuspendedAt(LocalDateTime.now());
    Users savedUser = userRepository.save(user);

    log.info("User suspended successfully with ID: {}", id);

    return UserMapper.toDto(savedUser);
  }

  @Override
  public UserResponse activateUser(Long id) throws Exception {
    log.info("Activating user with ID: {}", id);

    Users user = getUserById(id);
    user.setStatus(UserStatus.ACTIVE);
    user.setSuspendedAt(null);
    Users savedUser = userRepository.save(user);

    log.info("User activated successfully with ID: {}", id);

    return UserMapper.toDto(savedUser);
  }

  @Override
  public UserResponse deleteUser(Long id) throws Exception {
    log.info("Soft deleting user with ID: {}", id);

    Users user = getUserById(id);
    user.setStatus(UserStatus.DELETED);
    user.setDeletedAt(LocalDateTime.now());
    Users savedUser = userRepository.save(user);

    log.info("User soft deleted successfully with ID: {}", id);

    return UserMapper.toDto(savedUser);
  }

}
