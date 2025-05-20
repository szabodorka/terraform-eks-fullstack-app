package com.codecool.askmateoop.dao;


import com.codecool.askmateoop.dao.model.Answer;

import java.util.List;

public class AnswersDaoJdbc implements AnswersDAO {

    @Override
    public List<Answer> getAllAnswers() {
        // TODO SQL query questions from database
        throw new UnsupportedOperationException();
    }

    @Override
    public Answer getAnswer(int id){
        throw new UnsupportedOperationException();
    }

    @Override
    public void createAnswer(Answer answer){
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAnswer(int id){
        throw new UnsupportedOperationException();
    }
}
