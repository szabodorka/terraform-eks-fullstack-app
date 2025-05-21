package com.codecool.askmateoop.dao.model;

import java.time.LocalDateTime;

public record User(int id, String username, String password, LocalDateTime registrationDate, int score) {
}
