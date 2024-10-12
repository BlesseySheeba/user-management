package com.example.user_management.controller;

import com.example.user_management.model.User;
import com.example.user_management.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Method to list all users
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "userList"; // Ensure this matches your userList.html template
    }

    // Method to display the user creation form
    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user_form"; // This returns the user_form.html template
    }

    // Method to handle the creation of a new user
    @PostMapping
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/api/users"; // Redirect to user list after creation
    }

    // Method to display the user edit form
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable String id, Model model) {
        User user = userService.getUserById(id)
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        model.addAttribute("user", user);
        return "editUser"; // Change this to your editUser.html template
    }

    // Method to handle the update of an existing user
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable String id, @ModelAttribute User user) {
        user.setId(id); // Set the ID of the user to ensure the correct user is updated
        userService.updateUser(id, user); // Pass both ID and User object
        return "redirect:/api/users"; // Redirect to user list after update
    }

    // Method to handle the deletion of a user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "redirect:/api/users"; // Redirect to user list after deletion
    }
}
