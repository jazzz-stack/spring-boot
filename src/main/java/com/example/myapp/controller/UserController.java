package com.example.myapp.controller;

import com.example.myapp.entity.User;
import com.example.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("create-user")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        userService.saveEntry(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("all-user")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PutMapping("update-user/{userName}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String userName) {
     User existingUserInDb = userService.findByUsername(userName);
     if (existingUserInDb != null) {
         existingUserInDb.setPassword(user.getPassword());
         existingUserInDb.setUsername(user.getUsername());
         userService.updateEntry(existingUserInDb);
         return new ResponseEntity<>(existingUserInDb, HttpStatus.OK);
     }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("user-by-id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("delete-user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id) {
        User deletedUser = userService.deleteEntryById(id);
        if (deletedUser != null) {
            return new ResponseEntity<>(deletedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
