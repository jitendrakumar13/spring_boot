//package com.springboot.journalApp.controller;
//
//import com.springboot.journalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//@RestController
//@RequestMapping("/journal")
//public class JournalEntryController {
//
//    private Map<Long, JournalEntry>journalEntries=new HashMap<>();
//
//    @GetMapping
//    public List<JournalEntry> getAll(){
//        return new ArrayList<>(journalEntries.values());
//    }
//    @PostMapping
//    public JournalEntry createEntry(@RequestBody JournalEntry myJournalEntry){
//        journalEntries.put(myJournalEntry.getId(),myJournalEntry);
//        return myJournalEntry;
//    }
//
//    @GetMapping("id/{myid}")
//    public JournalEntry getEntryById(@PathVariable Long myid){
//        return journalEntries.get(myid);
//    }
//@DeleteMapping("id/{myid}")
//    public void deleteEntryById(@PathVariable Long myid){
//        journalEntries.remove(myid);
//    }
//}
