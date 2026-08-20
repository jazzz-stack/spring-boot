package com.example.myapp.service;

import com.example.myapp.entity.JournalEntry;
import com.example.myapp.entity.User;
import com.example.myapp.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, String userName) {
        User user = userService.findByUsername(userName);
        journalEntry.setCreatedAt(LocalDateTime.now());
        JournalEntry saved = journalRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.updateEntry(user);
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

    public void deleteJournalEntryById(String id, String userName) {
        User user = userService.findByUsername(userName);
//        user.getJournalEntries().remove(getEntryById(id)); // simpler way
        user.getJournalEntries().removeIf(entry -> entry.getId().equals(id)); // conditional way
        userService.saveEntry(user);
        journalRepository.deleteById(id);

    }
}
