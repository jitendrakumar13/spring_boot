package com.springboot.journalApp.service;


import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// controller -> service -> repository
//The `JournalEntryController` handles HTTP requests and calls the `JournalEntryService`.
// - The `JournalEntryService` contains the business logic and calls the `JournalEntryRepository`.
//- The `JournalEntryRepository` interacts with the database to fetch the data.

//@Component // This annotation tells Spring that this class is a Spring-managed bean and should be instantiated when the application starts.
@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;
    @Transactional
    public void  saveEntry(JournalEntry myJournalEntry, String username){

        try {
            User user=userService.findByUsername(username);
            myJournalEntry.setDate(LocalDateTime.now());
            JournalEntry saved= journalEntryRepository.save(myJournalEntry);
            user.getJournalEntries().add(saved);
            System.out.println("user: "+user);
            userService.saveUser(user);
        } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Error saving entry",e);
        }
    }

    public void  saveEntry(JournalEntry myJournalEntry){
        journalEntryRepository.save(myJournalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId myid){
        return journalEntryRepository.findById(myid);
    }
    public void  UpdateEntry(JournalEntry newJournalEntry){
        journalEntryRepository.save(newJournalEntry);
    }
@Transactional
    public boolean deleteById(ObjectId myid, String username){
        boolean removed= false;
        try {
            User user=userService.findByUsername(username);
             removed= user.getJournalEntries().removeIf(x -> x.getId().equals(myid));
            if(removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(myid);
            }
        } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Error deleting entry",e);
        }

       return removed;

    }

}
