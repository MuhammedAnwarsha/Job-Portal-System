package com.jb.security;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jb.model.Users;
import com.jb.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Users user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found " + username);
    }

    GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
    Collection<GrantedAuthority> grantedAuthorities = Collections.singletonList(authority);
    return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
  }
  
  

}
