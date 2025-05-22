package com.codecool.askmateoop.service;

import com.codecool.askmateoop.controller.dto.answer.AnswerDTO;
import com.codecool.askmateoop.dao.AnswersDAO;
import com.codecool.askmateoop.dao.model.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

public class AnswerServiceTest {
    private AnswersDAO dao;
    private AnswerService service;
    private List<Answer> testAnswers;
    private List<AnswerDTO> testAnswerDTOs;
    private boolean isExceptionNeeded;


    class MockedDao implements AnswersDAO{
        public MockedDao() {
        }

        @Override
        public List<Answer> getAllAnswers() {
            if (isExceptionNeeded){
                throw new RuntimeException("mocked exception throw");
            } else {
                return testAnswers;
            }
        }

        @Override
        public Answer getAnswer(int id) {
            return null;
        }

        @Override
        public void createAnswer(Answer answer) {

        }

        @Override
        public void deleteAnswer(int id) {

        }


    }

    @BeforeEach
    public void setup(){
        dao = new MockedDao();
        service = new AnswerService(dao);
        isExceptionNeeded = false;
    }

    @Test
    public void getAllAnswers_whenAnswersFound_thenReturnRightList(){
        testAnswers =  List.of(
                new Answer(1,"title1", "message1", 1, LocalDateTime.parse("2025-01-01T12:00"), 1)
        );
        testAnswerDTOs =  List.of(
                new AnswerDTO(1,"title1", "message1", 1, LocalDateTime.parse("2025-01-01T12:00"), 1)
        );

        List<AnswerDTO> result = service.getAllAnswers();
        assertEquals(testAnswerDTOs, result);
    }

    @Test
    public void getAllAnswers_whenAnswerNotFound_thenReturnEmptyList(){
        testAnswers = List.of();
        List<AnswerDTO> result = service.getAllAnswers();
        testAnswerDTOs = List.of();
        assertEquals(testAnswerDTOs, result);
    }

    @Test
    public void getAllAnswers_whenDoaThrowsException_thenThrowsRuntimeException(){
        isExceptionNeeded = true;
        assertThrows(RuntimeException.class, ()->service.getAllAnswers());
    }
}
