package com.codecool.askmateoop.dao.model;

import com.codecool.askmateoop.controller.dto.answer.NewAnswerDTO;

import java.time.LocalDateTime;

public record Answer(int id, String title, String message,int userId, LocalDateTime postDate, int questionId) {
    public static Answer fromNewAnswerDTO(NewAnswerDTO newAnswerDTO){
        return new Answer(
                -1,
                newAnswerDTO.title(),
                newAnswerDTO.message(),
                newAnswerDTO.userId(),
                null,
                newAnswerDTO.questionId()
        );
    }
}
