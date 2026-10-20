package com.jb.js.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jb.domain.UserRole;

@RestController
public class HomeController {

  @GetMapping
  public String homeController() {
    return "Job Portal Job Service" + UserRole.ROLE_ADMIN;
  }
}

