package com.cip.controllers;

import com.cip.dao.user.UserRepository;
import com.cip.model.Role;
import com.cip.model.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model, @RequestParam String roleForSet) {
        User userFromDb = userRepository.findByLogin(user.getLogin());

        if (userFromDb != null) {
            model.put("message", "User exists");
            return "registration";
        }
        user.setActive(true);
        if (roleForSet.equals("Инженер")) {
            user.setRoles(Collections.singleton(Role.ENGINEER));
        } else {
            user.setRoles(Collections.singleton(Role.OPERATOR));
        }
        userRepository.save(user);
        return "engineer";
    }
}
