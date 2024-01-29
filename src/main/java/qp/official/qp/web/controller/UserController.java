package qp.official.qp.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.service.UserService;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/sign_up/kakao")
    public String signUp(Model model){
        String redirectUrl = userService.toKakaoLogin();
        model.addAttribute("kakaoLoginUrl", redirectUrl);
        return "login";
    }
}
