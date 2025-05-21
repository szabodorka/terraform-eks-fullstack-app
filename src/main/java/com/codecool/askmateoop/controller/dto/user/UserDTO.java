package com.codecool.askmateoop.controller.dto.user;

import com.codecool.askmateoop.dao.model.User;

import java.time.LocalDateTime;

public record UserDTO(int id, String username, LocalDateTime registrationDate, int score) {

  public static UserDTO fromUser(User user) {
    if(user == null) {
      return null;
    }
    return new UserDTO(
            user.id(),
            user.username(),
            user.registrationDate(),
            user.score()
    );
  }

}
