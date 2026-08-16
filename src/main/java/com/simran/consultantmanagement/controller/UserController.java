package com.simran.consultantmanagement.controller;

import com.simran.consultantmanagement.entity.User;
import com.simran.consultantmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String users(Model model) {

        model.addAttribute(
                "users",
                userService.getAllUsers()
        );

        return "users";
    }

    @GetMapping("/new")
    public String newUser(Model model) {

        model.addAttribute(
                "user",
                new User()
        );

        return "user-form";
    }

    @PostMapping("/save")
    public String saveUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult result) {

        if (result.hasErrors()) {
            return "user-form";
        }

        userService.saveUser(user);

        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "user",
                userService.getUserById(id)
        );

        return "user-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return "redirect:/users";
    }
}