package com.codecool.askmateoop.dao.user;

import com.codecool.askmateoop.dao.model.User;

import java.util.List;

public interface UsersDAO {
  List<User> getAllUsers();
  boolean saveUser(User user);
  boolean exists(User user);
  boolean passwordMatches(User user);
  boolean deleteUserById(int id);
  User getUserById(int id);
  int getUserIdByUsername(String username);
  int increaseUserScoreById(int id, int scoreDiff);
}
