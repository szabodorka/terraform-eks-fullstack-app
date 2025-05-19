package com.codecool.askmateoop.configuration;

import com.codecool.askmateoop.dao.QuestionsDAO;
import com.codecool.askmateoop.dao.QuestionsDaoJdbc;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class Configuration {

//    TODO: Add the url of your database to the Environment Variables of the Run Configuration
//    @Value("${askmate.database.url}")
//    private String databaseUrl;

    @Bean
    public QuestionsDAO questionsDAO() {
        return new QuestionsDaoJdbc();
    }

}
