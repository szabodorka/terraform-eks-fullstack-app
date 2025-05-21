package com.codecool.askmateoop.dao.user;

import com.codecool.askmateoop.dao.model.User;

import javax.sql.DataSource;
import java.sql.*;
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
    String sql = "SELECT * FROM \"user\";";

    try (Connection conn = dataSource.getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)
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
      throw new RuntimeException("SQL Error: could not get users.", e);
    }
    return users;
  }

  @Override
  public void saveUser(User user) {
    String sql = "INSERT INTO \"user\" (username, password, score) VALUES (?, ?, ?);";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement pst = conn.prepareStatement(sql)
    ) {
      pst.setString(1, user.username());
      pst.setString(2, user.password());
      pst.setInt(3, user.score());
      pst.executeUpdate();
    } catch(SQLException e) {
      throw new RuntimeException("SQL Error: could not save user.", e);
    }
  }

  @Override
  public boolean exists(User user) {
    String sql = "SELECT * FROM \"user\" WHERE username = ?;";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement pst = conn.prepareStatement(sql)
    ) {
      pst.setString(1, user.username());
      ResultSet rs = pst.executeQuery();
      return rs.next();
    } catch(SQLException e) {
      throw new RuntimeException("SQL Error: failed user validation.", e);
    }
  }

  @Override
  public boolean passwordMatches(User user) {
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
