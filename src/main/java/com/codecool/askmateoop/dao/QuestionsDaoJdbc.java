package com.codecool.askmateoop.dao;

import com.codecool.askmateoop.dao.model.Question;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QuestionsDaoJdbc implements QuestionsDAO {
    private final DataSource dataSource;

    public QuestionsDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Question> getAllQuestions() {
        // TODO SQL query questions from database
        throw new UnsupportedOperationException();
    }

    @Override
    public Question getQuestion(int id) {
        return null;
    }

    @Override
    public void createQuestion(Question question) {

    }

    @Override
    public void deleteQuestion(int id) {

    }
}
