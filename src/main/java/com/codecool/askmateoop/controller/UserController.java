package com.codecool.askmateoop.controller;

import com.codecool.askmateoop.controller.dto.user.NewUserDTO;
import com.codecool.askmateoop.controller.dto.user.UserDTO;
import com.codecool.askmateoop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    try {
      List<UserDTO> users = userService.getAllUsers();
      if (users == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(users);
    } catch (RuntimeException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
    try {
      UserDTO user = userService.getUserById(id);
      if (user == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(user);
    } catch (RuntimeException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/name/{id}")
  public ResponseEntity<String> getUserNameById(@PathVariable int id) {
    try {
      UserDTO user = userService.getUserById(id);
      if (user == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(user.username());
    } catch (RuntimeException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/login")
  public ResponseEntity<Integer> login(@RequestParam String username, @RequestParam String password) {
    NewUserDTO user = new NewUserDTO(username, password);
    try {
      int userId = userService.loginUser(user);
      if (userId == -1) {
        return ResponseEntity.badRequest().build();
      }
      return ResponseEntity.ok(userId);
    } catch (RuntimeException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/score")
  public ResponseEntity<Integer> increaseScore(@RequestParam int userId, @RequestParam int scoreDiff) {
    try {
        int newScore = userService.increaseUserScoreById(userId, scoreDiff);
        if (newScore == -1) {
          return ResponseEntity.badRequest().build();
        }
      return ResponseEntity.ok(newScore);
    } catch (RuntimeException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("/")
  public ResponseEntity<Void> addNewUser(@RequestBody NewUserDTO user) {
    try {
      boolean successful = userService.addNewUser(user);
      if (successful) {
        return ResponseEntity.ok().build();
      } else {
        return ResponseEntity.badRequest().build();
      }
    } catch (RuntimeException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUserById(@PathVariable int id) {
    try {
      boolean successful = userService.deleteUserById(id);
      if (successful) {
        return ResponseEntity.noContent().build();
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (RuntimeException e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
