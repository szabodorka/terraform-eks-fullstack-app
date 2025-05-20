package com.codecool.askmateoop.service;

import com.codecool.askmateoop.controller.dto.answer.NewAnswerDTO;
import com.codecool.askmateoop.controller.dto.answer.AnswerDTO;
import com.codecool.askmateoop.dao.AnswersDAO;
import com.codecool.askmateoop.dao.model.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerService {

    private final AnswersDAO answersDAO;

    @Autowired
    public AnswerService(AnswersDAO answersDAO) {
        this.answersDAO = answersDAO;
    }

    public List<AnswerDTO> getAllAnswers() {
        List<Answer> allAnswers = answersDAO.getAllAnswers();
        // TODO convert data to AnswerDTO
        throw new UnsupportedOperationException();
    }

    public AnswerDTO getAnswerById(int id) {
        // TODO
        throw new UnsupportedOperationException();
    }

    public boolean deleteAnswerById(int id) {
        // TODO
        throw new UnsupportedOperationException();
    }

    public int addNewAnswer(NewAnswerDTO answer) {
        // TODO
        throw new UnsupportedOperationException();
    }
}
