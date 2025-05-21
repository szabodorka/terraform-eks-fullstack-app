package com.codecool.askmateoop.service;

import com.codecool.askmateoop.controller.dto.question.NewQuestionDTO;
import com.codecool.askmateoop.controller.dto.question.QuestionDTO;
import com.codecool.askmateoop.dao.QuestionsDAO;
import com.codecool.askmateoop.dao.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        List<Question> allQuestions = questionsDAO.getAllQuestions();
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : allQuestions) {
            questionDTOs.add(new QuestionDTO(question.id(), question.title(), question.description(), question.userId(), question.postDate()));
        }
        return questionDTOs;
    }

    public QuestionDTO getQuestionById(int id) {
            Question question = questionsDAO.getQuestion(id);
        return new QuestionDTO(question.id(), question.title(), question.description(), question.userId(), question.postDate());
    }

    public void deleteQuestionById(int id) {
        Question question = questionsDAO.getQuestion(id);
        questionsDAO.deleteQuestion(id);
    }

    public NewQuestionDTO addNewQuestion(NewQuestionDTO question) {
        int userId = 1;  // from session
        LocalDateTime now = LocalDateTime.now();

        Question questionToSave = new Question(0, question.title(), question.description(), userId, now);
        Question savedQuestion = questionsDAO.createQuestion(questionToSave);

        return new NewQuestionDTO(
                savedQuestion.title(),
                savedQuestion.description(),
                savedQuestion.userId()
        );
    }
}
