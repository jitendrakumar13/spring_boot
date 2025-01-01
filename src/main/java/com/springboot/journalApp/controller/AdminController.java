package com.springboot.journalApp.controller;

import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.UserRepository;
import com.springboot.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired // This is a dependency injection annotation that tells Spring to inject the JournalEntryService object into this class
    private UserService userService;

    @Autowired
    UserRepository userRepository;;

    @GetMapping
    public ResponseEntity<?> getAllUser(){
        List<User> all = userService.getAll();

        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<?> updateUser( @RequestBody User user){

        userService.saveAdmin(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/makeadmin")
    public ResponseEntity<?> makeAdmin(@RequestBody User user){

        User userIndb = userService.findByUsername(user.getUsername());
        System.out.println(user);
        if (userIndb!=null) {
            userIndb.setRoles(List.of("ADMIN"));
            userService.saveUser(userIndb);
            return new ResponseEntity<>(userIndb, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }



}
