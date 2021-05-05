package com.cip.controllers;

import com.cip.dao.cip.*;
import com.cip.dao.user.UserRepository;
import com.cip.model.cip.Cip;
import com.cip.model.dto.Efficiency;
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

import java.util.HashMap;
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

    @GetMapping("/gantt")
    public String showGanttDiagram(Model model) {
        Map<Integer, String[]> data;

     /*   long id = 1;
        id = cipService.TestDataBaseCip(id, 1, 1, 1, 4, 4, 0, 0, 1);
        id = cipService.TestDataBaseCip(id, 2, 1, 7, 4 ,4, 1, 2, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 1, 11, 4 ,4, 2, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 1, 16, 4 ,4, 2, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 1, 20, 4, 4, 3, 7, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 1, 5, 4, 4, 3, 52, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 1, 6, 4, 4, 4, 2, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 1, 2, 4, 4, 4, 52, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 1, 3, 4, 4,5, 4, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 1, 4, 4, 4, 5, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 1, 8, 4, 4, 6, 8, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 1, 9, 4, 4, 6, 52, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 1, 10, 4, 4, 7, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 1, 12, 4, 4, 7, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 1, 13, 4, 4, 8, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 1, 14, 4, 4, 8, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 1, 15, 4, 4, 9, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 1, 10, 4, 4, 9, 55, (int) (1 + id));
        id = 1;
        id = cipService.TestDataBaseCip(id, 3, 2, 25, 4, 4, 0, 0, 1);
        id = cipService.TestDataBaseCip(id, 2, 2, 35, 4, 4, 1, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 2, 41, 4, 4, 2, 15, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 2, 40, 4, 4, 2, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 2, 40, 4, 4, 3, 15, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 2, 38, 4, 4, 3, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 2, 37, 4, 4, 4, 15, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 2, 32, 4, 4, 4, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 2, 31, 4, 4, 5, 15, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 2, 30, 4, 4, 5, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 2, 24, 4, 4, 6, 15, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 2, 26, 4, 4, 6, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 2, 27, 4, 4, 7, 15, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 2, 28, 4, 4, 7, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 2, 29, 4, 4, 8, 15, (int) (1 + id));
        id = 1;
        id = cipService.TestDataBaseCip(id, 1, 3, 45, 4, 4, 0, 0, 1);
        id = cipService.TestDataBaseCip(id, 2, 3, 51, 4, 4, 2, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 3, 43, 4, 4, 2, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 3, 44, 4, 4, 3, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 3, 46, 4, 4, 3, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 3, 47, 4, 4, 4, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 3, 48, 4, 4, 4, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 3, 49, 4, 4, 5, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 3, 50, 4, 4, 5, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 3, 52, 4, 4, 6, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 3, 53, 4, 4, 6, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 3, 54, 4, 4, 7, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 3, 57, 4, 4, 8, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 3, 58, 4, 4, 8, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 3, 59, 4, 4, 9, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 3, 61, 4, 4, 9, 55, (int) (1 + id));
        id = 1;
        id = cipService.TestDataBaseCip(id, 2, 4, 67, 4, 4, 0, 0, 1);
        id = cipService.TestDataBaseCip(id, 3, 4, 91, 4, 4, 1, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 4, 92, 4, 4, 2, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 4, 88, 4, 4, 2, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 4, 89, 4, 4, 3, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 4, 77, 4, 4, 3, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 4, 75, 4, 4, 4, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 4, 73, 4, 4, 4, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 4, 88, 4, 4, 5, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 4, 87, 4, 4, 5, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 4, 89, 4, 4, 6, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 1, 4, 82, 4, 4, 6, 55, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 2, 4, 80, 4, 4, 7, 5, (int) (1 + id));
        id = cipService.TestDataBaseCip(id, 3, 4, 85, 4, 4, 7, 55, (int) (1 + id));*/
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

    @PostMapping("/journal") // filter
    public @ResponseBody
    Map<Integer, String[]> p() {
        return null;
    }

    @GetMapping("/efficiency")
    public String showEfficiency() {
        return "efficiency";
    }

    @PostMapping("/efficiency") // filter
    public @ResponseBody
    Map<Integer, String[]> getDiagram(@RequestBody Efficiency efficiencyDTO) {
        Map <Integer, String []> s = cipService.getFilterEfficiency(efficiencyDTO);
        return s;
    }

    @GetMapping("/efficiency_circuit")
    public String showEfficiencyCircuit() {
        return "efficiency_circuit";
    }

    @PostMapping("/efficiency_circuit") // filter
    public @ResponseBody
    Map<Integer, String[]> getEfficiencyCircuit() {
        Map <Integer, String []> s = new HashMap<>();
        return s;
    }
}
