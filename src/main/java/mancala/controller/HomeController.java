package mancala.controller;

import mancala.room.Room;
import mancala.user.UserForm;
import mancala.user.UserStore;
import mancala.room.RoomService;
import mancala.utils.SessionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

import static mancala.render.ModelAttributes.LOGIN;
import static mancala.render.ModelAttributes.USERNAME;

/**
 * Created by Alexander on 12/14/2023
 */
@Controller
public class HomeController {

    @Autowired
    private UserStore userStore;
    @Autowired
    private RoomService gameRoomService;
    @Autowired
    SessionTools sessionTools;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("userForm", new UserForm("Enter User Name"));
        String tabId = UUID.randomUUID().toString();
        model.addAttribute("tabId", tabId);
        return "index";
    }
    @GetMapping("/back")
    public String backHome(@RequestParam String tabId, Model model) {
        sessionTools.populateModel(model, tabId);
        return "index";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String tabId, Model model, @ModelAttribute("userForm") UserForm userForm) {
        String userName = userForm.userName();
        if (userStore.registerUser(userName)) {
            sessionTools.setAttribute(tabId, USERNAME, userName);
            sessionTools.setUserIdentifier(userName, tabId);
            model.addAttribute(LOGIN, userName);
            List<Room> games = gameRoomService.fetchGames();
            model.addAttribute("games", games);
        } else {
            model.addAttribute("login_error", "Username already taken");
        }
        return "index";
    }

}
