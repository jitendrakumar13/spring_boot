package com.springboot.journalApp.controller;

import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.service.JournalEntryService;
import com.springboot.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired // This is a dependency injection annotation that tells Spring to inject the JournalEntryService object into this class
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

//    @GetMapping
//    public ResponseEntity<?> getAll(){
//        List<JournalEntry> journalEntries = journalEntryService.getAll();
//       if(journalEntries!=null){
//           return new ResponseEntity<>(journalEntries, HttpStatus.OK);
//       }
//       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user=userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myid)).toList();
        if(!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myid);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }

    @GetMapping //get journal entries by username
    public ResponseEntity<?>getAllJournalEntriesByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user=userService.findByUsername(username);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if(allEntries!=null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>("No content", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public  ResponseEntity<?> createEntry(@RequestBody JournalEntry myJournalEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
         try {
            journalEntryService.saveEntry(myJournalEntry,username);
            return new ResponseEntity<>(myJournalEntry, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("id/{myid}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myid, @RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user=userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myid)).toList();
        if(!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myid);
            if (journalEntry.isPresent()) {
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);

            }
        }

        JournalEntry old = journalEntryService.findById(myid).orElse(null);
    if (old!=null) {
        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
        old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
        journalEntryService.saveEntry(old);
        return new ResponseEntity<>(old, HttpStatus.OK);
    }
    return new ResponseEntity<>( HttpStatus.NOT_FOUND);
}

    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?>deleteEntry(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
       boolean removed= journalEntryService.deleteById(myid,username);
        if (removed) return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }

}
