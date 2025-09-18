package com.codecool.askmateoop.controller.dto.question;

import java.time.LocalDateTime;

public record QuestionDTO(int id, String title, String description, int userId, LocalDateTime postDate) {}
