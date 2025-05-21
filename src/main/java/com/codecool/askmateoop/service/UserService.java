package com.codecool.askmateoop.service;

import com.codecool.askmateoop.controller.dto.user.NewUserDTO;
import com.codecool.askmateoop.controller.dto.user.UserDTO;
import com.codecool.askmateoop.dao.user.UsersDAO;
import com.codecool.askmateoop.dao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
  private final UsersDAO usersDAO;

  @Autowired
  public UserService(UsersDAO usersDAO) {
    this.usersDAO = usersDAO;
  }

  public List<UserDTO> getAllUsers() {
    List<User> allUsers = usersDAO.getAllUsers();
    // TODO convert data to UserDTO
    return List.of(new UserDTO(1, "Example Title", "Example Description", LocalDateTime.now(), 0));
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
