package com.codecool.askmateoop.dao;


import com.codecool.askmateoop.dao.model.Answer;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AnswersDaoJdbc implements AnswersDAO {
    private final DataSource dataSource;

    public AnswersDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Answer> getAllAnswers() {
        try(Connection connection = dataSource.getConnection()){
            String sql ="SELECT id, title, message, user_id, post_date, question_id FROM answer";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Answer> answers = new ArrayList<>();
            while (rs.next()){
                answers.add(new Answer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getTimestamp(5).toLocalDateTime(),
                        rs.getInt(6)
                ));
            }
            return answers;
        }catch (SQLException e){
            throw new RuntimeException("sql error: cannot get all answers", e);
        }
    }

    @Override
    public List<Answer> getAllAnswersByQuestionId(int questionId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, title, message, user_id, post_date, question_id FROM answer WHERE question_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, questionId);
            ResultSet rs = st.executeQuery();

            List<Answer> answers = new ArrayList<>();
            while (rs.next()) {
                answers.add(new Answer(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("message"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("post_date").toLocalDateTime(),
                        rs.getInt("question_id")
                ));
            }
            return answers;
        } catch (SQLException e) {
            throw new RuntimeException("SQL error: cannot get answers for question ID: " + questionId, e);
        }
    }

    @Override
    public Answer getAnswer(int id) {
        try(Connection connection = dataSource.getConnection()){
            String sql ="SELECT id, title, message, user_id, post_date, question_id FROM answer WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                String title = rs.getString(2);
                String message = rs.getString(3);
                int userId = rs.getInt(4);
                LocalDateTime postDate = rs.getTimestamp(5).toLocalDateTime();
                int questionId = rs.getInt(6);
                Answer answer = new Answer(id, title, message, userId, postDate, questionId);
                return answer;
            } else {
                return null;
            }
        }catch (SQLException e){
            throw new RuntimeException("sql error: cannot get answer", e);

        }
    }

    @Override
    public void createAnswer(Answer answer){
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO answer (title, message, user_id, question_id) VALUES (?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, answer.title());
            st.setString(2, answer.message());
            st.setInt(3, answer.userId());
            st.setInt(4, answer.questionId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("sql error: cannot create answers", e);
        }
    }

    @Override
    public void deleteAnswer(int id){
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM answer WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, id);
            st.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException("sql error: cannot delete answer", e);
        }
    }
}
