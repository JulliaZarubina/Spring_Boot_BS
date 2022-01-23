package web.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import web.model.Role;
import web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.service.RoleService;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "adminpage";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "new";
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
