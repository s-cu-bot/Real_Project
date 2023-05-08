package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.service.UserService;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "login/index";
    }

    @RequestMapping("/register")
    public String register() {
        return "login/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("userid") String userid,
                               @RequestParam("password") String password) {
        userService.registerUser(username, userid, password);
        return "redirect:/login";
    }

    @RequestMapping("/user")
    public String user() {
        return "user/index";
    }
}