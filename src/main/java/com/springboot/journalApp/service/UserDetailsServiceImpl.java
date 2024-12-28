package com.springboot.journalApp.service;

import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if(user!=null){
            UserDetails userDetails =org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
            return userDetails;
        }
        throw new UsernameNotFoundException("User not found" + username);
    }
}
//explain all the codes in this file and how they work together
//The `UserDetailsService` interface is used to retrieve user-related data from a data source. It has a single method, `loadUserByUsername()`, which is used to load a user by their username.
// The `UserDetailsServiceImpl` class implements the `UserDetailsService` interface. It overrides the `loadUserByUsername()` method to load a user from the `UserRepository` based on their username.
// The `UserRepository` is autowired into the `UserDetailsServiceImpl` class to provide access to user data stored in the database.
// The `loadUserByUsername()` method retrieves a user from the database using the `UserRepository` and constructs a `UserDetails` object using the retrieved user's data.
//
