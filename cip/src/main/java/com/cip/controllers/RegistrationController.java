package com.cip.controllers;

import com.cip.dao.user.UserRepository;
import com.cip.model.user.User;
import com.cip.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final UserService userService;

    public RegistrationController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(User user, Map<String, Object> model, @RequestParam String roleForSet) {
        if (userService.addUser(user, roleForSet)) {
            model.put("message", "User exists");
            return "registration";
        }
        return "engineer";
    }
}
