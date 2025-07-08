package com.yusssss.mail_subscription.business.concretes;

import com.yusssss.mail_subscription.business.abstracts.AuthService;
import com.yusssss.mail_subscription.business.abstracts.UserService;
import com.yusssss.mail_subscription.core.security.JwtService;
import com.yusssss.mail_subscription.entities.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthManager implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthManager(AuthenticationManager authenticationManager,
                       @Lazy UserService userService,
                       @Lazy JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(String email, String password) {

        User user = userService.getUserByEmail(email);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), password)
        );

        if (authentication.isAuthenticated()) {;
            return jwtService.generateToken(user.getUsername(), user.getAuthorities());
        } else {
            throw new RuntimeException();
        }

    }

    @Override
    public void register(User user) {
        userService.saveUser(user);
    }
}
