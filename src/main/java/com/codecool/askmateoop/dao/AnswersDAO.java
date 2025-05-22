package com.codecool.askmateoop.dao;

import com.codecool.askmateoop.dao.model.Answer;
import java.util.List;

public interface AnswersDAO {
    List<Answer> getAllAnswers();
    List<Answer> getAllAnswersByQuestionId(int questionId);
    Answer getAnswer(int id);
    void createAnswer(Answer answer);
    void deleteAnswer(int id);
}
