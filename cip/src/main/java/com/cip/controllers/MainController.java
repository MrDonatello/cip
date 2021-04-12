package com.cip.controllers;

import com.cip.dao.cip.*;
import com.cip.dao.user.UserRepository;
import com.cip.model.cip.Cip;
import com.cip.model.dto.GanttDTO;
import com.cip.model.user.User;
import com.cip.service.CipService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private final CipService cipService;

    public MainController(UserRepository userRepository, Cip1Repository cip1Repository, Cip2Repository cip2Repository, Cip3Repository cip3Repository, Cip4Repository cip4Repository, CommonRepository commonRepository, WarningRepository warningRepository, CipService cipService) {
        this.userRepository = userRepository;
        this.cip1Repository = cip1Repository;
        this.cip2Repository = cip2Repository;
        this.cip3Repository = cip3Repository;
        this.cip4Repository = cip4Repository;
        this.commonRepository = commonRepository;
        this.warningRepository = warningRepository;
        this.cipService = cipService;
    }


    @GetMapping("/login")
    public String login() {
        return "login";
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

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }


    /*@GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/greeting")
    public String showDashboard(Model model) {
        Map<String, Integer> data = new LinkedHashMap<String, Integer>();
        data.put("JAVA", 50);
        data.put("Ruby", 20);
        data.put("Python", 30);

        model.addAttribute("data", data);
        return "greeting";
    }*/

    @GetMapping("/gantt")
    public String showGanttDiagram(Model model) {
        Map<Integer, String[]> data;

       /* long id = 1;
        id = cipService.TestDataBaseCip(id, 1, 1, 1, 3, 12, 12, 0, 1);
        id = cipService.TestDataBaseCip(id, 2, 1, 7, 3, 12, 11, 2, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 1, 11, 3, 12, 0, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 1, 16, 3, 12, 1, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 1, 20, 3, 12, 21, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 1, 5, 3, 12, 18, 5, (int) (1 + id));
        id = 1;
        id = cipService.TestDataBaseCip(id, 3, 2, 25, 3, 12, 1, 0, 1);
        id = cipService.TestDataBaseCip(id, 2, 2, 35, 3, 12, 2, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 2, 41, 3, 12, 2, 15, (int) (1 + id));
        id = 1;
        id = cipService.TestDataBaseCip(id, 3, 3, 45, 3, 12, 2, 0, 1);
        id = cipService.TestDataBaseCip(id, 2, 3, 55, 3, 12, 2, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 3, 51, 3, 12, 2, 15, (int) (1 + id));
        id = 1;
        id = cipService.TestDataBaseCip(id, 1, 4, 64, 3, 12, 2, 0, 1);
        id = cipService.TestDataBaseCip(id, 2, 4, 65, 3, 12, 2, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 4, 75, 3, 12, 2, 15, (int) (1 + id));*/
        data = cipService.getAllCipLogOneDay();
        model.addAttribute("data", data);
        return "gantt";
    }

    @PostMapping("/gantt")
    public @ResponseBody
    Map<Integer, String[]> objectData(@RequestBody GanttDTO ganttDTO) {
        if (ganttDTO.getStart() != null) {
            return cipService.getPropertyElement(ganttDTO);
        } else {
            return cipService.getCipFilter(ganttDTO);
        }
    }


    @GetMapping("/journal")
    public String showJournal(Model model) {
        Map<Integer, String[]> data;
        data = cipService.getAllCipLogOneDay();
        model.addAttribute("data", data);
        return "journal";
    }

    @PostMapping("/journal")
    public @ResponseBody
    Map<Integer, String[]> p() {
        return null;
    }
}
