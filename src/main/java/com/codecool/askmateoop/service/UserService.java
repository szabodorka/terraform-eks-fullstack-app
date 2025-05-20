package com.codecool.askmateoop.service;

import com.codecool.askmateoop.controller.dto.user.NewUserDTO;
import com.codecool.askmateoop.controller.dto.user.UserDTO;
import com.codecool.askmateoop.dao.user.UsersDAO;
import com.codecool.askmateoop.dao.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class UserService {
  private final UsersDAO usersDAO;

  @Autowired
  public UserService(UsersDAO usersDAO) {
    this.usersDAO = usersDAO;
  }

  public List<UserDTO> getAllUsers() {
    List<User> allUsers = usersDAO.getAllUsers();
    return allUsers.stream().map(UserDTO::fromUser).toList();
  }

  public UserDTO getUserById(int id) {
    // TODO
    throw new UnsupportedOperationException();
  }

  public boolean deleteUserById(int id) {
    // TODO
    throw new UnsupportedOperationException();
  }

  public int addNewUser(NewUserDTO user) {
    // TODO
    throw new UnsupportedOperationException();
  }
}
