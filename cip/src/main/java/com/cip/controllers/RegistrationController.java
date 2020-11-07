package com.cip.controllers;

import com.cip.model.Role;
import com.cip.model.User;
import com.cip.dao.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private  UserRepository userRepository;




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
