package com.example.myapp.service;

import com.example.myapp.entity.JournalEntry;
import com.example.myapp.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;


    public String saveEntry(JournalEntry journalEntry) {
        String originalEmail = journalEntry.getEmail();
        if (originalEmail != null) {
            UUID uuidFromName = UUID.nameUUIDFromBytes(originalEmail.getBytes());
            String generatedId = uuidFromName.toString();
            if (journalRepository.existsById(generatedId)) {
                return "Entry already exists"; // Message to return
            }
            journalEntry.setId(generatedId);
        }
        journalRepository.save(journalEntry);
        return "Saved successfully";
    }



    public List<JournalEntry> getAllEntries() {
        if (journalRepository.findAll().isEmpty()) {
            return List.of(); // Return an empty list if no entries are found
        }
        return journalRepository.findAll();
    }

    public JournalEntry getEntryById(String id) {
        return journalRepository.findById(id).orElse(null);
    }

    public List<JournalEntry> getByParam(String id) {
        return journalRepository.findById(id).stream().toList();
    }

    public JournalEntry updateEntry(JournalEntry journalEntry) {
        return journalRepository.save(journalEntry);
    }

    public JournalEntry deleteEntryById(String id) {
        JournalEntry journalEntry = journalRepository.findById(id).orElse(null);
        if (journalEntry != null) {
            journalRepository.delete(journalEntry);
        }
        return journalEntry;
    }
}
