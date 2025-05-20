package com.codecool.askmateoop.dao.model;

import java.time.LocalDateTime;

public record Answer(int id, String title, String description, LocalDateTime created) {
}
