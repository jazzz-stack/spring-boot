package com.example.myapp.repository;

import com.example.myapp.entity.MyAppEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MyAppRepository extends MongoRepository<MyAppEntry, String> {
}
