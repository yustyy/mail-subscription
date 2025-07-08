package com.yusssss.mail_subscription.webAPI.controllers;

import com.yusssss.mail_subscription.business.abstracts.UserService;
import com.yusssss.mail_subscription.core.results.DataResult;
import com.yusssss.mail_subscription.core.results.Result;
import com.yusssss.mail_subscription.core.results.SuccessDataResult;
import com.yusssss.mail_subscription.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<DataResult<List<User>>> getAllUsers(HttpServletRequest request) {
        var result = userService.getAllUsers();
        return ResponseEntity.ok(new SuccessDataResult<>(result, HttpStatus.OK, request.getRequestURI()));
    }

    @GetMapping("/getUser")
    public ResponseEntity<DataResult<User>> getUser(@RequestParam(required = false) UUID id,
                                                    @RequestParam(required = false) String username,
                                                    @RequestParam(required = false) String email,
                                                    HttpServletRequest request) {
        User user;
        if (id != null) {
            user = userService.getUserById(id);
        } else if (username != null) {
            user = userService.getUserByUsername(username);
        } else if (email != null) {
            user = userService.getUserByEmail(email);
        } else {
            throw new IllegalArgumentException("Bir parametre (id, username veya email) verilmelidir.");
        }
        return ResponseEntity.ok(new SuccessDataResult<>(user, HttpStatus.OK, request.getRequestURI()));
    }

    @PostMapping("/saveUser")
    public ResponseEntity<DataResult<User>> saveUser(@RequestBody User user, HttpServletRequest request) {
        var saved = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessDataResult<>(saved, HttpStatus.CREATED, request.getRequestURI()));
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<DataResult<User>> deleteUser(@RequestParam UUID id, HttpServletRequest request) {
        var deleted = userService.deleteUser(id);
        return ResponseEntity.ok(new SuccessDataResult<>(deleted, HttpStatus.OK, request.getRequestURI()));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<DataResult<User>> updateUser(@RequestParam UUID id, @RequestBody User user, HttpServletRequest request) {
        user.setId(id);
        var updated = userService.updateUser(user);
        return ResponseEntity.ok(new SuccessDataResult<>(updated, HttpStatus.OK, request.getRequestURI()));
    }

    @GetMapping("/getAuthenticatedUser")
    public ResponseEntity<DataResult<User>> getAuthenticatedUser(HttpServletRequest request) {
        var user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(new SuccessDataResult<>(user, HttpStatus.OK, request.getRequestURI()));
    }

    @GetMapping("/getUsersBySubscriptionEnabled")
    public ResponseEntity<DataResult<List<User>>> getUsersBySubscriptionEnabled(@RequestParam boolean enabled, HttpServletRequest request) {
        var users = userService.getUsersBySubscriptionEnabled(enabled);
        return ResponseEntity.ok(new SuccessDataResult<>(users, HttpStatus.OK, request.getRequestURI()));
    }
}