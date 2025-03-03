package com.ns.solve.controller;

import com.ns.solve.domain.User;
import com.ns.solve.domain.dto.MessageEntity;
import com.ns.solve.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<MessageEntity> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(new MessageEntity("User created successfully", createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageEntity> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(new MessageEntity("User retrieved successfully", user));
    }

    @GetMapping("/users/sorted-by-score")
    public ResponseEntity<MessageEntity> getUsersSortedByScore(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<User> users = userService.getUsersSortedByScore(page, size);
        return new ResponseEntity<>(new MessageEntity("Users fetched successfully", users), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<MessageEntity> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new MessageEntity("Users retrieved successfully", users));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageEntity> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(new MessageEntity("User updated successfully", updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageEntity> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageEntity("User deleted successfully", null));
    }
}
