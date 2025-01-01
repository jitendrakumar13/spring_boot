package com.springboot.journalApp.controller;

import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
     private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        try {
            User Checkuser= userService.findByUsername(user.getUsername());
            if (Checkuser!=null) { // Assuming a method exists to check if user already exists
                return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
            }
            userService.saveNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
