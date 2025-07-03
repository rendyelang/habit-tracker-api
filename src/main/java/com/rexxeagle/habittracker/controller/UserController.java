package com.rexxeagle.habittracker.controller;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rexxeagle.habittracker.config.JwtUtil;
import com.rexxeagle.habittracker.dto.UserDTO;
import com.rexxeagle.habittracker.entity.RefreshToken;
import com.rexxeagle.habittracker.entity.Users;
import com.rexxeagle.habittracker.repository.UserRepository;
import com.rexxeagle.habittracker.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Sign Up endpoint
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO) {
        try {
            // Object implementation
            Users user = new Users();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            userService.saveUser(user);
            return ResponseEntity.status(201).body(
                Map.of(
                    "message", "User registered successfully"
                    )
                );
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(500).body(
                Map.of(
                    "message", "Error registering user",
                    "error", e.getMessage()
                )
            );
        }
    }

    // Get User Profile endpoint
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            Users user = userService.findById(id);

            if (user != null) {
                return ResponseEntity.ok(
                    Map.of(
                        "id", user.getUserId(),
                        "name", user.getName(),
                        "email", user.getEmail()
                    )
                );
            } else {
                return ResponseEntity.status(404).body(
                    Map.of(
                        "message", "User not found"
                    )
                );
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                Map.of(
                    "message", "Error retrieving user",
                    "error", e.getMessage()
                )
            );
        }
    }

    // Sign In endpoint
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody UserDTO userDTO) {
        try {
            Users user = userService.findByEmail(userDTO.getEmail());

            if (user != null && passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                // Generate JWT access token (valid for 15 minutes)
                String accessToken = JwtUtil.generateToken(user.getEmail(), 15 * 60 * 1000, "access");

                // Generate JWT refresh token (valid for 7 days)
                String refreshToken = JwtUtil.generateToken(user.getEmail(), 7 * 24 * 60 * 60 * 1000, "refresh");
                Date createdAt = new Date(System.currentTimeMillis());
                Date expiresAt = new Date(createdAt.getTime() + 7 * 24 * 60 * 60 * 1000);

                // Save refresh token in the database
                RefreshToken rToken = new RefreshToken(refreshToken, createdAt, expiresAt, user);
                userService.saveRefreshToken(rToken);

                return ResponseEntity.ok(
                    Map.of(
                        "message", "User signed in successfully",
                        "email", user.getEmail(),
                        "accessToken", accessToken,
                        "refreshToken", refreshToken
                    )
                );
            } else {
                return ResponseEntity.status(401).body(
                    Map.of(
                        "message", "Invalid email or password"
                    )
                );
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                Map.of(
                    "message", "Error signing in",
                    "error", e.getMessage()
                )
            );
        }
    }

    // Edit User Profile endpoint
    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> editUserProfile(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            Users user = userService.findById(id);

            if (user != null) {
                // Data before update
                Map<String, String> beforeUpdate = Map.of(
                    "name", user.getName(),
                    "email", user.getEmail()
                );

                user.setName(userDTO.getName());
                user.setEmail(userDTO.getEmail());
                userService.saveUser(user);

                // Data after update
                Map<String, String> afterUpdate = Map.of(
                    "name", user.getName(),
                    "email", user.getEmail()
                );

                return ResponseEntity.ok(
                    Map.of(
                        "message", "User profile updated successfully",
                        "beforeUpdate", beforeUpdate,
                        "afterUpdate", afterUpdate
                    )
                );
            } else {
                return ResponseEntity.status(404).body(
                    Map.of(
                        "message", "User not found"
                    )
                );
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                Map.of(
                    "message", "Error updating user profile",
                    "error", e.getMessage()
                )
            );
        }
    }
}
