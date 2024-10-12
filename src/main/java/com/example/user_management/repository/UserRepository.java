package com.example.user_management.repository;

import com.example.user_management.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    // Custom query methods can be added here if needed
}
