package com.example.user_management.service;

import com.example.user_management.exception.ResourceNotFoundException;
import com.example.user_management.model.User;
import com.example.user_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Method to retrieve a user by ID
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    // Method to create a new user
    public User createUser(User user) {
        // Ensure that the ID is null, allowing MongoDB to generate it
        user.setId(null); // Assuming the User model has a setter for ID
        return userRepository.save(user);
    }

    // Method to update an existing user
    public User updateUser(String id, User userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(userDetails.getFirstName());
                    existingUser.setLastName(userDetails.getLastName());
                    existingUser.setPhoneNumber(userDetails.getPhoneNumber());
                    existingUser.setEmail(userDetails.getEmail());
                    existingUser.setAddress(userDetails.getAddress());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)); // Throw custom exception if not found
    }

    // Method to delete a user by ID
    public void deleteUser(String id) {
        // Check if the user exists before trying to delete
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        // Directly delete by ID
        userRepository.deleteById(id);
    }
}
