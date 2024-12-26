package com.springboot.journalApp.controller;

import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.service.JournalEntryService;
import com.springboot.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired // This is a dependency injection annotation that tells Spring to inject the JournalEntryService object into this class
    private UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }
    @PostMapping
    public  ResponseEntity<User> createUser(@RequestBody User user){
        try {
            userService.saveEntry(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("id/{myid}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User user){
        User userIndb = userService.findByUsername(username);
        if (userIndb!=null) {
            userIndb.setUsername(user.getUsername());
            userIndb.setPassword(user.getPassword() );
            userService.saveEntry(userIndb);
            return new ResponseEntity<>(userIndb, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }


}
