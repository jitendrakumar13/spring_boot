package com.springboot.journalApp.repository;

import com.springboot.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

//spring mongo db repository provides a interface to interact with the database
//here <JournalEntry,String> first is the entity class and second is the primary key type
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {


}
