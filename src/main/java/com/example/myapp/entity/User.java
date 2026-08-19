package com.example.myapp.entity;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;
    @Indexed(unique = true) // Lombok validation throw error
    @NonNull // Lombok validation throw error
    private String username;
    @NonNull // Lombok validation throw error
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
}
