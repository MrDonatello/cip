package com.cip.controllers;

import com.cip.dao.cip.*;
import com.cip.dao.user.UserRepository;
import com.cip.model.cip.*;
import com.cip.model.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final Cip1Repository cip1Repository;
    private final Cip2Repository cip2Repository;
    private final Cip3Repository cip3Repository;
    private final Cip4Repository cip4Repository;
    private final CommonRepository commonRepository;
    private final WarningRepository warningRepository;

    public MainController(UserRepository userRepository, Cip1Repository cip1Repository, Cip2Repository cip2Repository, Cip3Repository cip3Repository, Cip4Repository cip4Repository, CommonRepository commonRepository, WarningRepository warningRepository) {
        this.userRepository = userRepository;
        this.cip1Repository = cip1Repository;
        this.cip2Repository = cip2Repository;
        this.cip3Repository = cip3Repository;
        this.cip4Repository = cip4Repository;
        this.commonRepository = commonRepository;
        this.warningRepository = warningRepository;
    }


    @GetMapping("/")
    public String login() {
        List<Cip1> cip1 = cip1Repository.findAll();
        List<Cip2> cip2 = cip2Repository.findAll();
        List<Cip3> cip3 = cip3Repository.findAll();
        List<Cip4> cip4 = cip4Repository.findAll();
        List<Common> commons = commonRepository.findAll();
        List<Warning> warnings = warningRepository.findAll();
        return "login";
    }

    @GetMapping("/login")
    public String login2() {
        return "login"; // redirect ???
    }

    @GetMapping("/success")
    public String loginPageRedirect(@AuthenticationPrincipal User user) {
        if (user.getRoles().toString().contains("ENGINEER")) {
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

  /*  @PostMapping("/logout")
    public String logout() {
        return "login";
    }
*/


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
