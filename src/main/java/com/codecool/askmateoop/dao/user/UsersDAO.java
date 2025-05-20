package com.codecool.askmateoop.dao.user;

import com.codecool.askmateoop.dao.model.User;

import java.util.List;

public interface UsersDAO {
  List<User> getAllUsers();
  void saveUser(User user);
  void deleteUserById(int id);
  User getUserById(int id);
}
