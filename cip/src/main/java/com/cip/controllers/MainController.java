package com.cip.controllers;

import com.cip.dao.cip.Cip2Repository;
import com.cip.dao.cip.CipRepository;
import com.cip.dao.user.UserRepository;
import com.cip.model.cip.Cip;
import com.cip.model.cip.Cip1;
import com.cip.model.cip.Cip2;
import com.cip.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final UserRepository userRepository;
    private final CipRepository cipRepository;
    private final Cip2Repository cip2Repository;

    public MainController(UserRepository userRepository, CipRepository cipRepository, Cip2Repository cip2Repository) {
        this.userRepository = userRepository;
        this.cipRepository = cipRepository;
        this.cip2Repository = cip2Repository;
    }


    @GetMapping("/")
    public String login() {
        List<Cip1> cip1 = cipRepository.findAll();
        List<Cip2> cip2 = cip2Repository.findAll();

        return "login";
    }

    @GetMapping("/success")
    public String loginPageRedirect(Authentication authentication) {
        String role = authentication.getAuthorities().toString();
        if (role.contains("ENGINEER")) {
            return "engineer";
        } else {
            return "operator";
        }
    }

    /*@PostMapping("/")
    public String str(@RequestParam String username, @RequestParam String password, Map<String,Object> model) {
        System.out.println(model);
        return "engineer";
    }*/
    /*@GetMapping("/login")
    public String ho(Model model) {
       Iterable<User> users = userRepository.findAll();
        return "login";
    }*/

    @GetMapping("/engineer")
    public String test(Model model) {
        /*Iterable<User> users = userRepository.findAll();*/
        return "engineer";
    }

    @PostMapping("/engineer")
    public String hom(Cip cip1) {

        Iterable<User> users = userRepository.findAll();
        return "engineer";
    }



    /*@GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }*/

    @GetMapping("/greeting")
    public String showDashboard(Model model) {
        Map<String, Integer> data = new LinkedHashMap<String, Integer>();
        data.put("JAVA", 50);
        data.put("Ruby", 20);
        data.put("Python", 30);

        model.addAttribute("data", data);
        return "greeting";
    }
}
