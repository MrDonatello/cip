package com.cip.service;

import com.cip.dao.user.UserRepository;
import com.cip.model.Role;
import com.cip.model.user.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login);
    }

    public boolean addUser(User user, String role) {
        User userFromDb = userRepository.findByLogin(user.getLogin());
        if (userFromDb != null) {
            return true;
        }
        if (role.equals("Инженер")) {
            user.setRoles(Collections.singleton(Role.ENGINEER));
        } else {
            user.setRoles(Collections.singleton(Role.OPERATOR));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return false;
    }
}
