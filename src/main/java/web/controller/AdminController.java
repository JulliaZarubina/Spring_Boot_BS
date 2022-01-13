package web.controller;

import web.model.Role;
import web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    @Qualifier(value = "userServiceImpl")
    private UserService userService;

    @GetMapping
    public String allUsers(Model model) {
        List<User> allUsers = userService.allUsers();
        model.addAttribute("allUsers",userService.allUsers());
        return "userpage";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/userinf";
    }

    @PostMapping
    public String newUser(@ModelAttribute("user") User user,
                             @RequestParam(required = false, name = "ADMIN") String admin,
                             @RequestParam(required = false, name = "USER") String USER) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2L, USER));
        if (admin != null) {
            roles.add(new Role(1L, admin));
        }
        user.setRoles(roles);
        userService.add(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                             @RequestParam(required = false, name = "ADMIN") String admin,
                             @RequestParam(required = false, name = "USER") String USER) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2L, USER));
        if (admin != null) {
            roles.add(new Role(1L, admin));
        }
        user.setRoles(roles);
        userService.edit(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/admin";
    }
}
