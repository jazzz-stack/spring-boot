package com.example.myapp.service;

import com.example.myapp.entity.User;
import com.example.myapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public String saveEntry(User user) {
        String originalName = user.getUsername();
        UUID uuidFromName = UUID.nameUUIDFromBytes(originalName.getBytes());
        String generatedId = uuidFromName.toString();
        if (userRepository.existsById(generatedId)) {
            return "Entry already exists"; // Message to return
        }
        user.setId(generatedId);
        userRepository.save(user);
        return "Saved successfully";
    }

    public ResponseEntity<?> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(404).body("No record found");
        } else {
            return ResponseEntity.ok(users);
        }
    }

    public User getById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getByParam(String id) {
        return userRepository.findById(id).stream().toList();
    }

    public User updateEntry( User user) {
        return userRepository.save(user);
    }

    public User deleteEntryById(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
