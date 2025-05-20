package com.codecool.askmateoop.controller.dto.user;

import com.codecool.askmateoop.dao.model.User;

import java.time.LocalDateTime;

public record UserDTO(String username, LocalDateTime registrationDate, int score) {
  public static UserDTO fromUser(User user) {
    return new UserDTO(
            user.username(),
            user.registrationDate(),
            user.score()
    );
  }
}
