package com.codecool.askmateoop.controller.dto.answer;

import com.codecool.askmateoop.dao.model.Answer;

import java.time.LocalDateTime;

public record AnswerDTO(int id, String title, String message, int userId, LocalDateTime postDate, int questionId) {

    public static AnswerDTO convertFromAnswer(Answer answer){
        return new AnswerDTO(
                answer.id(),
                answer.title(),
                answer.message(),
                answer.userId(),
                answer.postDate(),
                answer.questionId()
        );
    }
}
