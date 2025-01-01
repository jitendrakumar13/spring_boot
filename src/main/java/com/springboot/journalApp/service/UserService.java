package com.springboot.journalApp.service;


import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;


   private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userRepository.save(user);
    }
    public void saveAdmin(User user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER","ADMIN"));
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId myid){
        return userRepository.findById(myid);
    }
    public void saveUser(User user){
        userRepository.save(user);
    }
    public void deleteById(ObjectId myid){
        userRepository.deleteById(myid);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
