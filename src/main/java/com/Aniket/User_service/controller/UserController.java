package com.Aniket.User_service.controller;

import com.Aniket.User_service.model.User;
import com.Aniket.User_service.model.dto.AuthRequest;
import com.Aniket.User_service.model.dto.UserDto;
import com.Aniket.User_service.service.iUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.auth.BearerToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserController {


    private final iUserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            log.info("user registration request user {}:", user);
            // Check if user with this email already exists
            User existingUser = userService.findByEmail(user.getEmail());
            if (existingUser != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists");
            }
            
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userService.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            log.error("Error registering user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user: " + e.getMessage());
        }
    }
    @PostMapping("/validate")
    public ResponseEntity<UserDto> validateUser(@RequestBody AuthRequest req) {
        log.info("request body i user service {}", req);
        try {
            // Handle the case where multiple users might have the same email
            List<User> users = userService.findAllByEmail(req.getEmail());
            
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            // Use the first user with matching credentials
            for (User user : users) {
                if (passwordEncoder.matches(req.getPassword(), user.getPassword())) {
                    UserDto dto = new UserDto(
                            user.getId(),
                            user.getEmail(),
                            user.getUsername(),
                            user.getRole()
                    );
                    return ResponseEntity.ok(dto);
                }
            }
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            log.error("Error validating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @SecurityRequirement(name = "BearerAuth")
//    @GetMapping("/getAll")
//    public ResponseEntity<List<User>> getAllUsers(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String auth){
//        return userService.findAllUsers();
//    }
//    @SecurityRequirement(name = "BearerAuth")
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUser(@PathVariable String id,@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String auth) {
//        return ResponseEntity.ok(userService.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found")));
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.findAllUsers();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }


}
