package com.example.service;

import com.example.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void saveUser(User user) {
        // Logic for saving the user (can be integrated with database if necessary)
        System.out.println("User saved: " + user.getUsername());
    }
}
