package com.example.myapp.controller;

import com.example.myapp.entity.MyAppEntry;
import com.example.myapp.service.MyAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyAppController {

    @Autowired
    private MyAppService myAppService;

    @PostMapping("/create")
    public ResponseEntity<?> createEntry(@RequestBody MyAppEntry myAppEntry) {
        try {
            myAppEntry.setCreatedAt(LocalDateTime.now());
            // Use the service's return message to determine the response
            String result = myAppService.saveEntry(myAppEntry);
            if ("Entry already exists".equals(result)) {
                // Return 409 Conflict with the message
                return new ResponseEntity<>(result, HttpStatus.CONFLICT);
            }
            // Return 201 Created with the saved object
            return new ResponseEntity<>(myAppEntry, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEntries() {
        List<MyAppEntry> myAppEntries = myAppService.getAllEntries();
        if (myAppEntries.isEmpty()) {
            return new ResponseEntity<>("No record found",HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(myAppEntries, HttpStatus.OK);
        }
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<MyAppEntry> getEntryById(@PathVariable String id) {
        MyAppEntry myAppEntry = myAppService.getEntryById(id);
        if (myAppEntry != null) {
            return new ResponseEntity<>(myAppEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-by-param")
    public ResponseEntity<List<MyAppEntry>> getByParam(@RequestParam String id) {
        List<MyAppEntry> myAppEntries = myAppService.getByParam(id);
        if (myAppEntries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(myAppEntries, HttpStatus.OK);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<MyAppEntry> updateEntry(@RequestBody MyAppEntry myAppEntry) {
        if(myAppEntry.getId() != null) {
            MyAppEntry old = myAppService.getEntryById(myAppEntry.getId());
            old.setName(myAppEntry.getName() != null && myAppEntry.getName().isEmpty() ? myAppEntry.getName() : old.getName());
            old.setAddress(myAppEntry.getAddress() != null && myAppEntry.getAddress().isEmpty() ? myAppEntry.getAddress() : old.getAddress());
            old.setEmail(myAppEntry.getEmail() != null && !myAppEntry.getEmail().isEmpty() ? myAppEntry.getEmail() : old.getEmail());
            old.setPhone(myAppEntry.getPhone() != null && myAppEntry.getPhone().isEmpty() ? myAppEntry.getPhone() : old.getPhone());
            return new ResponseEntity<>(myAppService.updateEntry(old), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MyAppEntry> deleteEntry(@PathVariable String id) {
        MyAppEntry myAppEntry = myAppService.deleteEntryById(id);
        if (myAppEntry != null) {
            return new ResponseEntity<>(myAppEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
