package com.yusssss.mail_subscription.webAPI.controllers;

import com.yusssss.mail_subscription.business.abstracts.AuthService;
import com.yusssss.mail_subscription.core.constants.AuthMessages;
import com.yusssss.mail_subscription.core.results.DataResult;
import com.yusssss.mail_subscription.core.results.Result;
import com.yusssss.mail_subscription.core.results.SuccessDataResult;
import com.yusssss.mail_subscription.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<DataResult<String>> generateToken(@RequestBody LoginUserRequest loginUserRequest, HttpServletRequest request) {
        var result = authService.login(loginUserRequest.getEmail(), loginUserRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessDataResult<>(result, AuthMessages.TOKEN_GENERATED_SUCCESSFULLY, HttpStatus.OK, request.getRequestURI()));
    }

    @PostMapping("/register")
    public ResponseEntity<Result> register(@RequestBody User user, HttpServletRequest request) {
        authService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessDataResult<>(AuthMessages.USER_REGISTERED_SUCCESSFULLY, HttpStatus.CREATED, request.getRequestURI()));
    }

}
