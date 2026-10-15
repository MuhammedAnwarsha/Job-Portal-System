package com.jb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jb.model.Users;

public interface UserRepository extends JpaRepository<Users, Long>{

  Users findByEmail(String email);
  
  boolean existsByEmail(String email);
}
