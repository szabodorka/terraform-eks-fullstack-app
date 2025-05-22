package com.codecool.askmateoop.dao;

import com.codecool.askmateoop.dao.model.Question;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionsDaoJdbc implements QuestionsDAO {
    private final DataSource dataSource;

    public QuestionsDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Question> getAllQuestions() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, title, description, user_id, post_date FROM question ORDER BY post_date DESC";
            ResultSet result = connection.createStatement().executeQuery(sql);
            List<Question> allQuestions = new ArrayList<>();
            while (result.next()) {
                Question question = new Question(result.getInt("id"), result.getString("title"), result.getString("description"), result.getInt("user_id"), result.getTimestamp("post_date").toLocalDateTime());
                allQuestions.add(question);
            }
            return allQuestions;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all questions", e);
        }
    }

    @Override
    public Question getQuestion(int id) {
        try (Connection connection = dataSource.getConnection()){
            String sql = "SELECT * FROM question WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                int userId = resultSet.getInt("user_id");
                LocalDateTime postDate = resultSet.getTimestamp("post_date").toLocalDateTime();
                return new Question(id, title, description, userId, postDate);
            } else {
                return null;
            }
        } catch (SQLException e){
            throw new RuntimeException("Error while reading question", e);
        }
    }

    @Override
    public void createQuestion(Question question) {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO question (title, description, user_id) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, question.title());
            statement.setString(2, question.description());
            statement.setInt(3, question.userId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while adding new question", e);
        }
    }

    @Override
    public void deleteQuestion(int id) {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM question WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting question", e);
        }
    }

    @Override
    public List<Question> getSearchedQuestions(String searchTerm) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, title, description, user_id, post_date FROM question WHERE title ILIKE ? OR description ILIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + searchTerm + "%");
            preparedStatement.setString(2, "%" + searchTerm + "%");

            ResultSet result = preparedStatement.executeQuery();

            List<Question> allQuestions = new ArrayList<>();
            while (result.next()) {
                Question question = new Question(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getInt("user_id"),
                        result.getTimestamp("post_date").toLocalDateTime()
                );
                allQuestions.add(question);
            }
            return allQuestions;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading searched questions", e);
        }
    }
}
