package com.example.myapp.entity;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "myapp_entries")
@Getter // Lombok generate getters at compile time
@Setter // Lombok generate setters at compile time
//@Data // Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
public class MyAppEntry {
    @Id
    private String id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    @NonNull // Lombok validation and will throw error if null
    private String title;
    private String description;

}
