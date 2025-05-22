package com.codecool.askmateoop.dao;

import com.codecool.askmateoop.dao.model.Question;

import java.util.List;

public interface QuestionsDAO {
    List<Question> getAllQuestions();
    Question getQuestion(int id);
    void createQuestion(Question question);
    void deleteQuestion(int id);
    List<Question> getSearchedQuestions(String searchTerm);
}
