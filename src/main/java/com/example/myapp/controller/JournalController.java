package com.example.myapp.controller;

import com.example.myapp.entity.JournalEntry;
import com.example.myapp.entity.User;
import com.example.myapp.service.JournalService;
import com.example.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class JournalController {

    @Autowired
    private JournalService journalService;
    @Autowired
    private UserService userService;


    @PostMapping("/create/{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName) {
        try {
            journalService.saveEntry(journalEntry, userName);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/get-all/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        try {
            User user = userService.findByUsername(userName);
            if (user != null) {
                List<JournalEntry> journalEntries = user.getJournalEntries();
                if (journalEntries.isEmpty()) {
                    return new ResponseEntity<>("No journal entries found for user: " + userName, HttpStatus.NOT_FOUND);
                } else {
                    return new ResponseEntity<>(journalEntries, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("User not found: " + userName, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable String id) {
        JournalEntry journalEntry = journalService.getEntryById(id);
        if (journalEntry != null) {
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-by-param")
    public ResponseEntity<List<JournalEntry>> getByParam(@RequestParam String id) {
        List<JournalEntry> myAppEntries = journalService.getByParam(id);
        if (myAppEntries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(myAppEntries, HttpStatus.OK);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<JournalEntry> updateEntry(@RequestBody JournalEntry journalEntry) {
        if(journalEntry.getId() != null) {
            JournalEntry old = journalService.getEntryById(journalEntry.getId());
            old.setName(journalEntry.getName() != null && journalEntry.getName().isEmpty() ? journalEntry.getName() : old.getName());
            old.setAddress(journalEntry.getAddress() != null && journalEntry.getAddress().isEmpty() ? journalEntry.getAddress() : old.getAddress());
            old.setEmail(journalEntry.getEmail() != null && !journalEntry.getEmail().isEmpty() ? journalEntry.getEmail() : old.getEmail());
            old.setPhone(journalEntry.getPhone() != null && journalEntry.getPhone().isEmpty() ? journalEntry.getPhone() : old.getPhone());
            return new ResponseEntity<>(journalService.updateEntry(old), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<JournalEntry> deleteEntry(@PathVariable String id) {
        JournalEntry journalEntry = journalService.deleteEntryById(id);
        if (journalEntry != null) {
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
