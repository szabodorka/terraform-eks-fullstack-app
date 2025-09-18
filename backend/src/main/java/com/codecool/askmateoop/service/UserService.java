package com.codecool.askmateoop.service;

import com.codecool.askmateoop.controller.dto.user.NewUserDTO;
import com.codecool.askmateoop.controller.dto.user.UserDTO;
import com.codecool.askmateoop.dao.user.UsersDAO;
import com.codecool.askmateoop.dao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private final UsersDAO usersDAO;

  @Autowired
  public UserService(UsersDAO usersDAO) {
    this.usersDAO = usersDAO;
  }

  public List<UserDTO> getAllUsers() {
    try {
      List<User> allUsers = usersDAO.getAllUsers();
      if (allUsers.isEmpty()) {
        return null;
      }
      return allUsers.stream().map(UserDTO::fromUser).toList();
    } catch (RuntimeException e) {
      throw new RuntimeException("Service Error: could not get all users.", e);
    }
  }

  public UserDTO getUserById(int id) {
    try {
      return UserDTO.fromUser(usersDAO.getUserById(id));
    } catch (RuntimeException e) {
      throw new RuntimeException("Service Error: could not get user by id.", e);
    }
  }

  public int increaseUserScoreById(int id, int scoreDiff) {
    try {
      return usersDAO.increaseUserScoreById(id, scoreDiff);
    } catch (RuntimeException e) {
      throw new RuntimeException("Service Error: could not increase user score.", e);
    }
  }

  public boolean deleteUserById(int id) {
    try {
      if (!usersDAO.exists(usersDAO.getUserById(id))) {
        return false;
      }
      return usersDAO.deleteUserById(id);
    } catch (RuntimeException e) {
      throw new RuntimeException("Service Error: could not delete user.", e);
    }
  }

  public boolean addNewUser(NewUserDTO newUserDTO) {
    try {
      User user = User.fromNewDTO(newUserDTO);
      if (usersDAO.exists(user)) {
        return false;
      }
      return usersDAO.saveUser(user);
    } catch (RuntimeException e) {
      throw new RuntimeException("Service Error: could not save user.", e);
    }
  }

  public int loginUser(NewUserDTO newUserDTO) {
    try {
      User user = User.fromNewDTO(newUserDTO);
      if (!usersDAO.exists(user) || !usersDAO.passwordMatches(user)) {
        return -1;
      }
      return usersDAO.getUserIdByUsername(user.username());
    } catch (RuntimeException e) {
      throw new RuntimeException("Service Error: could not log in user.", e);
    }
  }

}
