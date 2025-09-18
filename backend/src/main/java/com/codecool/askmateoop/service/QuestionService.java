package com.codecool.askmateoop.service;

import com.codecool.askmateoop.controller.dto.question.NewQuestionDTO;
import com.codecool.askmateoop.controller.dto.question.QuestionDTO;
import com.codecool.askmateoop.dao.QuestionsDAO;
import com.codecool.askmateoop.dao.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionsDAO questionsDAO;

    @Autowired
    public QuestionService(QuestionsDAO questionsDAO) {
        this.questionsDAO = questionsDAO;
    }

    public List<QuestionDTO> getAllQuestions() {
        try {
            List<Question> allQuestions = questionsDAO.getAllQuestions();
            List<QuestionDTO> questionDTOs = new ArrayList<>();
            for (Question question : allQuestions) {
                questionDTOs.add(new QuestionDTO(question.id(), question.title(), question.description(), question.userId(), question.postDate()));
            }
            return questionDTOs;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while getting all questions", e);
        }
    }

    public QuestionDTO getQuestionById(int id) {
            try {
                Question question = questionsDAO.getQuestion(id);
                return new QuestionDTO(question.id(), question.title(), question.description(), question.userId(), question.postDate());
            } catch (RuntimeException e) {
                throw new RuntimeException("Error while getting question by id", e);
            }
    }

    public void deleteQuestionById(int id) {
        try {
            questionsDAO.deleteQuestion(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while deleting question", e);
        }
    }

    public void addNewQuestion(NewQuestionDTO question) {
        try {
            Question questionToSave = new Question(-1, question.title(), question.description(), question.userId(), null);
            questionsDAO.createQuestion(questionToSave);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while adding new question", e);
        }
    }

    public List<QuestionDTO> searchQuestionsBySearchTerm(String searchTerm) {
        try {
            List<Question> searchedQuestions = questionsDAO.getSearchedQuestions(searchTerm);
            List<QuestionDTO> questionDTOs = new ArrayList<>();
            for (Question question : searchedQuestions) {
                questionDTOs.add(new QuestionDTO(question.id(), question.title(), question.description(), question.userId(), question.postDate()));
            }
            return questionDTOs;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while searching for questions", e);
        }
    }
}
