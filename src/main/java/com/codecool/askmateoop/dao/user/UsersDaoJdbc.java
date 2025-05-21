package com.codecool.askmateoop.dao.user;

import com.codecool.askmateoop.dao.model.User;

import javax.sql.DataSource;
import java.util.List;

public class UsersDaoJdbc implements UsersDAO {
  private final DataSource dataSource;

  public UsersDaoJdbc(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public List<User> getAllUsers() {
    // TODO SQL query users from database
    throw new UnsupportedOperationException();
  }

  @Override
  public void saveUser(User user) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void deleteUserById(int id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public User getUserById(int id) {
    throw new UnsupportedOperationException();
  }
}
