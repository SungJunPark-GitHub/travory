package com.travory.app.user.controller;

import com.travory.app.user.dto.UserDto;
import com.travory.app.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String registerPage() {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(UserDto userDto,
                           org.springframework.ui.Model model) {

        try {
            userService.register(userDto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/register";
        }

        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(UserDto userDto,
                        HttpSession session) {

        UserDto loginUser = userService.login(userDto);

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        session.setAttribute("loginUser", loginUser);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }

}
