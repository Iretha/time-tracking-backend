package com.smdev.timetracking.ttbackend.controller;

import com.smdev.timetracking.ttbackend.core.exception.ResourceNotFoundException;
import com.smdev.timetracking.ttbackend.model.User;
import com.smdev.timetracking.ttbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

//    @GetMapping("/users")
//    public List<User> getAllUsers() {
//        return this.userRepository.findAll();
//    }

    @GetMapping("/users/login/{email}")
    public ResponseEntity<User> login(@PathVariable(value = "email") String email)
            throws ResourceNotFoundException {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for email :: " + email));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users/logout")
    public ResponseEntity<User> logout(@Valid @RequestBody User user) throws ResourceNotFoundException {
        //TODO
        return ResponseEntity.ok().body(new User());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for id :: " + id));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public  User createUser(@Valid @RequestBody User user) {
        User savedUser = this.userRepository.save(user);
        return savedUser;
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id,
                                           @Valid @RequestBody User updatedUser) throws ResourceNotFoundException {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        user.setName(updatedUser.getName());
        user.setNickname(updatedUser.getNickname());
        user.setEmail(updatedUser.getEmail());
        final User savedUser = this.userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for id :: " + id));
        this.userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
