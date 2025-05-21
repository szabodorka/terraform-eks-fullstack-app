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
  public List<UserDTO> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public UserDTO getUserById(@PathVariable int id) {
//        TODO
    throw new UnsupportedOperationException();
  }

  @GetMapping("/login")
  public ResponseEntity<Integer> login(@RequestParam String username, @RequestParam String password) {
    NewUserDTO user = new NewUserDTO(username, password);
    try {
      int userId = userService.loginUser(user);
      if(userId == -1) {
        return ResponseEntity.badRequest().build();
      }
      return ResponseEntity.ok(userId);
    } catch(RuntimeException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("/")
  public ResponseEntity<Void> addNewUser(@RequestBody NewUserDTO user) {
    try {
      boolean successful = userService.addNewUser(user);
      if(successful) {
        return ResponseEntity.ok().build();
      } else {
        return ResponseEntity.badRequest().build();
      }
    } catch(RuntimeException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @DeleteMapping("/{id}")
  public boolean deleteUserById(@PathVariable int id) {
//        TODO
    throw new UnsupportedOperationException();
  }
}
