package com.codecool.askmateoop.dao.user;

import com.codecool.askmateoop.dao.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsersDaoJdbc implements UsersDAO {
  private final DataSource dataSource;

  public UsersDaoJdbc(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM \"user\"";

    try (Connection conn = dataSource.getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql);
    ) {
      while(rs.next()) {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        LocalDateTime registrationDate = rs.getTimestamp("registration_date").toLocalDateTime();
        int score = rs.getInt("score");
        users.add(new User(id, username, password, registrationDate, score));
      }
    } catch(SQLException e) {
      throw new UnsupportedOperationException(e);
    }
    return users;
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
