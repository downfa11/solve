package com.ns.solve.service;

import com.ns.solve.domain.User;
import com.ns.solve.repository.user.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public Page<User> getUsersSortedByScore(int page, int size) {
        return userRepository.findAllByOrderByScoreDesc(PageRequest.of(page, size));
    }
    public User getUserByAccount(String account) {
        return userRepository.findByAccount(account);
    }
    public User updateUser(Long id, User userDetails) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setNickname(userDetails.getNickname());
            updatedUser.setAccount(userDetails.getAccount());
            updatedUser.setPassword(userDetails.getPassword());
            updatedUser.setScore(userDetails.getScore());
            return userRepository.save(updatedUser);
        }
        return null;
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
