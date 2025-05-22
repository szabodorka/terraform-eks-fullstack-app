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
      while (rs.next()) {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        LocalDateTime registrationDate = rs.getTimestamp("registration_date").toLocalDateTime();
        int score = rs.getInt("score");
        users.add(new User(id, username, password, registrationDate, score));
      }
      return users;
    } catch (SQLException e) {
      throw new RuntimeException("SQL Error: could not get all users.", e);
    }
  }

  @Override
  public boolean saveUser(User user) {
    String sql = "INSERT INTO \"user\" (username, password, score) VALUES (?, ?, ?);";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement pst = conn.prepareStatement(sql)
    ) {
      pst.setString(1, user.username());
      pst.setString(2, user.password());
      pst.setInt(3, user.score());
      pst.executeUpdate();
      return true;
    } catch (SQLException e) {
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
    } catch (SQLException e) {
      throw new RuntimeException("SQL Error: failed username validation.", e);
    }
  }

  @Override
  public boolean passwordMatches(User user) {
    String sql = "SELECT * FROM \"user\" WHERE username = ? AND password = ?;";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement pst = conn.prepareStatement(sql)
    ) {
      pst.setString(1, user.username());
      pst.setString(2, user.password());
      ResultSet rs = pst.executeQuery();
      return rs.next();
    } catch (SQLException e) {
      throw new RuntimeException("SQL Error: failed password validation.", e);
    }
  }

  @Override
  public int getUserIdByUsername(String username) {
    String sql = "SELECT id FROM \"user\" WHERE username = ?;";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement pst = conn.prepareStatement(sql)
    ) {
      pst.setString(1, username);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        return rs.getInt("id");
      } else {
        return -1;
      }
    } catch (SQLException e) {
      throw new RuntimeException("SQL Error: could not get user.", e);
    }
  }

  @Override
  public int increaseUserScoreById(int id, int scoreDiff) {
    try(Connection conn = dataSource.getConnection()){
      User user = getUserById(id);
      if (user == null) {
        return -1;
      }
      int newScore = user.score() + scoreDiff;
      String sql = "UPDATE \"user\" SET score = ? WHERE id = ?;";
      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setInt(1, newScore);
      pst.setInt(2, id);
      pst.executeUpdate();
      return newScore;
    } catch(SQLException e){
      throw new RuntimeException("SQL Error: could not increase user score.", e);
    }
  }

  @Override
  public boolean deleteUserById(int id) {
    String sql = "DELETE FROM \"user\" WHERE id = ?;";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement pst = conn.prepareStatement(sql)
    ) {
      pst.setInt(1, id);
      pst.executeUpdate();
      return true;
    } catch (SQLException e) {
      throw new RuntimeException("SQL Error: could not delete user.", e);
    }
  }

  @Override
  public User getUserById(int id) {
    User user = null;
    String sql = "SELECT * FROM \"user\" WHERE id = ?;";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement pst = conn.prepareStatement(sql)
    ) {
      pst.setInt(1, id);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        user = new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getTimestamp("registration_date").toLocalDateTime(),
                rs.getInt("score")
        );
      }
      return user;
    } catch (SQLException e) {
      throw new RuntimeException("SQL Error: could not get user.", e);
    }
  }
}
