package com.codecool.askmateoop.configuration;

import com.codecool.askmateoop.dao.AnswersDAO;
import com.codecool.askmateoop.dao.AnswersDaoJdbc;
import com.codecool.askmateoop.dao.QuestionsDAO;
import com.codecool.askmateoop.dao.QuestionsDaoJdbc;
import com.codecool.askmateoop.dao.user.UsersDAO;
import com.codecool.askmateoop.dao.user.UsersDaoJdbc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@SpringBootConfiguration
public class Configuration {

    //  TODO: Add the url of your database to the Environment Variables of the Run Configuration
    @Value("${askmate.database.url}")
    private String databaseUrl;

    @Value("${askmate.database.username}")
    private String databaseUsername;

    @Value("${askmate.database.password}")
    private String databasePassword;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUsername);
        dataSource.setPassword(databasePassword);
        return dataSource;
    }

    @Bean
    public AnswersDAO answersDAO(DataSource dataSource) {
        return new AnswersDaoJdbc(dataSource);
    }

    @Bean
    public QuestionsDAO questionsDAO(DataSource dataSource) {
        return new QuestionsDaoJdbc(dataSource);
    }

    @Bean
    public UsersDAO usersDAO(DataSource dataSource) {
        return new UsersDaoJdbc(dataSource);
    }

}
