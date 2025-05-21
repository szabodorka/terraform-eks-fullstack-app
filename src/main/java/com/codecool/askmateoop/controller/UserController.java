package com.codecool.askmateoop.controller;

import com.codecool.askmateoop.controller.dto.user.NewUserDTO;
import com.codecool.askmateoop.controller.dto.user.UserDTO;
import com.codecool.askmateoop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

  @PostMapping("/")
  public void addNewUser(@RequestBody NewUserDTO user) {
    userService.addNewUser(user);
  }

  @DeleteMapping("/{id}")
  public boolean deleteUserById(@PathVariable int id) {
//        TODO
    throw new UnsupportedOperationException();
  }
}
