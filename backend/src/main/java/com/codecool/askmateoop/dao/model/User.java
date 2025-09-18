package com.codecool.askmateoop.dao.model;

import com.codecool.askmateoop.controller.dto.user.NewUserDTO;

import java.time.LocalDateTime;

public record User(int id, String username, String password, LocalDateTime registrationDate, int score) {

  public static User fromNewDTO(NewUserDTO newUserDTO) {
    return new User(
            -1,
            newUserDTO.username(),
            newUserDTO.password(),
            null,
            0
    );
  }

}
