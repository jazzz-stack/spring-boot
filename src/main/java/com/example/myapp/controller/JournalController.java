package com.example.myapp.controller;

import com.example.myapp.entity.JournalEntry;
import com.example.myapp.service.JournalService;
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

    @PostMapping("/create")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            journalEntry.setCreatedAt(LocalDateTime.now());
            // Use the service's return message to determine the response
            String result = journalService.saveEntry(journalEntry);
            if ("Entry already exists".equals(result)) {
                // Return 409 Conflict with the message
                return new ResponseEntity<>(result, HttpStatus.CONFLICT);
            }
            // Return 201 Created with the saved object
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEntries() {
        List<JournalEntry> myAppEntries = journalService.getAllEntries();
        if (myAppEntries.isEmpty()) {
            return new ResponseEntity<>("No record found",HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(myAppEntries, HttpStatus.OK);
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
