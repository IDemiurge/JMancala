package mancala.controller;

import jakarta.servlet.http.HttpSession;
import mancala.user.UserForm;
import mancala.user.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Alexander on 12/17/2023
 */
// @RestController
public class UserController {

    @Autowired
    private UserStore userStore;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(Model model, @ModelAttribute("userForm") UserForm userForm, HttpSession session) {
        if (userStore.registerUser(userForm.userName())) {
            session.setAttribute("username", userForm.userName());
            model.addAttribute("login", userForm.userName());
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
        }
    }
    @PostMapping("/deregister")
    public ResponseEntity<String> deregisterUser(@RequestParam String username) {
        userStore.removeUser(username);
        return ResponseEntity.ok("User deregistered successfully");
    }
}
