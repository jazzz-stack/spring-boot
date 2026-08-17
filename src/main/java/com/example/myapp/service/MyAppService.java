package com.example.myapp.service;

import com.example.myapp.entity.MyAppEntry;
import com.example.myapp.repository.MyAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MyAppService {

    @Autowired
    private MyAppRepository myAppRepository;


    public String saveEntry(MyAppEntry myAppEntry) {
        String originalEmail = myAppEntry.getEmail();
        if (originalEmail != null) {
            UUID uuidFromName = UUID.nameUUIDFromBytes(originalEmail.getBytes());
            String generatedId = uuidFromName.toString();
            if (myAppRepository.existsById(generatedId)) {
                return "Entry already exists"; // Message to return
            }
            myAppEntry.setId(generatedId);
        }
        myAppRepository.save(myAppEntry);
        return "Saved successfully";
    }



    public List<MyAppEntry> getAllEntries() {
        if (myAppRepository.findAll().isEmpty()) {
            return List.of(); // Return an empty list if no entries are found
        }
        return myAppRepository.findAll();
    }

    public MyAppEntry getEntryById(String id) {
        return myAppRepository.findById(id).orElse(null);
    }

    public List<MyAppEntry> getByParam(String id) {
        return myAppRepository.findById(id).stream().toList();
    }

    public MyAppEntry updateEntry( MyAppEntry myAppEntry) {
        return myAppRepository.save(myAppEntry);
    }

    public MyAppEntry deleteEntryById(String id) {
        MyAppEntry myAppEntry = myAppRepository.findById(id).orElse(null);
        if (myAppEntry != null) {
            myAppRepository.delete(myAppEntry);
        }
        return myAppEntry;
    }
}
