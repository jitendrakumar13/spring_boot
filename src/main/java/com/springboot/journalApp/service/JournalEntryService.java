package com.springboot.journalApp.service;


import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void deleteById(ObjectId myid){
        journalEntryRepository.deleteById(myid);
    }

}
