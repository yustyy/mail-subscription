package com.yusssss.mail_subscription.business.concretes;

import com.yusssss.mail_subscription.business.abstracts.UserService;
import com.yusssss.mail_subscription.core.exceptions.UserNotFoundException;
import com.yusssss.mail_subscription.dataAccess.UserDao;
import com.yusssss.mail_subscription.entities.Role;
import com.yusssss.mail_subscription.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserManager implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;


    public UserManager(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return userDao.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(Set.of(Role.ROLE_USER));
        return userDao.save(user);
    }

    @Override
    public User deleteUser(UUID id) {
        User user = getUserById(id);
        userDao.delete(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        var existingUser = getUserById(user.getId());
        if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
        if (user.getFirstName() != null) existingUser.setFirstName(user.getFirstName());
        if (user.getLastName() != null) existingUser.setLastName(user.getLastName());
        if (user.getPassword() != null) existingUser.setPassword(user.getPassword());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
        existingUser.setSubscriptionEnabled(user.isSubscriptionEnabled());
        return userDao.save(existingUser);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotFoundException();
        }
        return getUserByUsername(authentication.getName());
    }

    @Override
    public List<User> getUsersBySubscriptionEnabled(boolean isSubscriptionEnabled) {
        return userDao.findAllBySubscriptionEnabled(isSubscriptionEnabled);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }
}
