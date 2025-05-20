package com.codecool.askmateoop.controller.dto.user;

import java.time.LocalDateTime;

public record UserDTO(int id, String username, String password, LocalDateTime registrationDate, int score) {
}
