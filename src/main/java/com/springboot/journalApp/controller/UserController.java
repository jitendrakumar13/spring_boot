package com.springboot.journalApp.controller;

import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.UserRepository;
import com.springboot.journalApp.service.JournalEntryService;
import com.springboot.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired // This is a dependency injection annotation that tells Spring to inject the JournalEntryService object into this class
    private UserService userService;

    @Autowired
    UserRepository userRepository;;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }


    @PutMapping
    public ResponseEntity<?> updateUser( @RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userIndb = userService.findByUsername(username);
        if (userIndb!=null) {
            userIndb.setUsername(user.getUsername());
            userIndb.setPassword(user.getPassword() );
            userService.saveEntry(userIndb);
            return new ResponseEntity<>(userIndb, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> DeleteUser( @RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername( authentication.getName());
        return new ResponseEntity<>("user deleted ", HttpStatus.OK);
     }

}
