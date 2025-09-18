package com.Aniket.User_service.controller;

import com.Aniket.User_service.model.User;
import com.Aniket.User_service.model.dto.AuthRequest;
import com.Aniket.User_service.model.dto.UserDto;
import com.Aniket.User_service.service.iUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final iUserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(userService.save(user));
    }
    @PostMapping("/validate")
    public ResponseEntity<UserDto> validateUser(@RequestBody AuthRequest req) {
        User user = userService.findByEmail(req.getEmail());
        if (user != null && passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            UserDto dto = new UserDto(
                    user.getId(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getRole()
            );
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers(){
        return userService.findAllUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

}
