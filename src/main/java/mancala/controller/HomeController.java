package mancala.controller;

import jakarta.servlet.http.HttpSession;
import mancala.room.GameRoom;
import mancala.user.UserForm;
import mancala.user.UserStore;
import mancala.room.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Created by Alexander on 12/14/2023
 */
@Controller
public class HomeController {

    @Autowired
    private UserStore userStore;
    @Autowired
    private GameRoomService gameRoomService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("userForm", new UserForm("Enter User Name"));
        return "index";
    }

    @PostMapping("/register")
    public String registerUser(Model model, @ModelAttribute("userForm") UserForm userForm, HttpSession session) {
        if (userStore.registerUser(userForm.userName())) {
            session.setAttribute("username", userForm.userName());
            model.addAttribute("login", userForm.userName());
            List<GameRoom> games = gameRoomService.fetchGames();
            model.addAttribute("games", games);
        } else {
            model.addAttribute("login_error", "Username already taken");
        }
            return "index";
    }

}
