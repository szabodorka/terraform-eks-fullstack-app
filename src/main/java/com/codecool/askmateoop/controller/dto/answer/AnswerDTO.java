package com.codecool.askmateoop.controller.dto.answer;

import java.time.LocalDateTime;

public record AnswerDTO(int id, String title, String message, int user_id, LocalDateTime post_date, int question_id) {
}
