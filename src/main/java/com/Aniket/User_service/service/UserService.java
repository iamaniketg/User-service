package com.Aniket.User_service.service;

import com.Aniket.User_service.model.User;
import com.Aniket.User_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements iUserService{
    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
