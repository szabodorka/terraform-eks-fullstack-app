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
        try {
        List<Answer> answers = answersDAO.getAllAnswers();
        return answers.stream()
                .map(AnswerDTO::convertFromAnswer)
                .toList();
        } catch (RuntimeException e){
            throw new RuntimeException("service error: cannot get all answers", e);
        }
    }

    public AnswerDTO getAnswerById(int id) {
        try {
        Answer answer = answersDAO.getAnswer(id);
        return AnswerDTO.convertFromAnswer(answer);
        } catch (RuntimeException e){
            throw new RuntimeException("service error: cannot get answer by id", e);
        }
    }

    public void deleteAnswerById(int id) {
        try{
            answersDAO.deleteAnswer(id);
        }catch (RuntimeException e){
            throw new RuntimeException("service error: cannot delete answer by id", e);
        }
    }

    public void addNewAnswer(NewAnswerDTO newAnswerDTO) {
        try {
            Answer answer = Answer.fromNewAnswerDTO(newAnswerDTO);
            answersDAO.createAnswer(answer);
        }  catch (RuntimeException e){
            throw new RuntimeException("service error: cannot add answer", e);
        }
    }
}
