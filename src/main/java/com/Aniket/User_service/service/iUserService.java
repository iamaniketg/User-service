package com.Aniket.User_service.service;

import com.Aniket.User_service.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface iUserService {
    User save(User user);
    Optional<User> findById(String id);

    ResponseEntity<List<User>> findAllUsers();

    User findByEmail(String username);
}
