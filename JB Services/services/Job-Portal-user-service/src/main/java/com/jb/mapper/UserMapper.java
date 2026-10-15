package com.jb.mapper;

import java.util.List;
import java.util.stream.Collectors;
import com.jb.dto.response.UserResponse;
import com.jb.model.Users;

public class UserMapper {

  public static UserResponse toDto(Users user) {
    UserResponse dto = new UserResponse();
    dto.setId(user.getId());
    dto.setEmail(user.getEmail());
    dto.setFullName(user.getFullName());
    dto.setPhone(user.getPhone());
    dto.setProfileImage(user.getProfileImage());
    dto.setRole(user.getRole());
    dto.setStatus(user.getStatus());
    dto.setLastLogin(user.getLastLogin());
    dto.setCreatedAt(user.getCreatedAt());
    
    return dto;
  }

  public static List<UserResponse> toDtoList(List<Users> users) {
    return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
  }
}
