package com.codecool.askmateoop.service;

import com.codecool.askmateoop.controller.dto.answer.AnswerDTO;
import com.codecool.askmateoop.controller.dto.answer.NewAnswerDTO;
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
    private Answer testAnswer;
    private List<Answer> testAnswers;
    private AnswerDTO testAnswerDTO;
    private List<AnswerDTO> testAnswerDTOs;
    private boolean isExceptionNeeded;
    private NewAnswerDTO newAnswerDTO;


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
            return testAnswer;
        }

        @Override
        public void createAnswer(Answer answer) {
            if (isExceptionNeeded){
                throw new RuntimeException("mocked exception throw");
            }
        }

        @Override
        public void deleteAnswer(int id) {
            if (isExceptionNeeded){
                throw new RuntimeException("mocked exception throw");
            }
        }


    }

    @BeforeEach
    public void setup(){
        dao = new MockedDao();
        service = new AnswerService(dao);
        isExceptionNeeded = false;
        testAnswer = new Answer(1,"title1", "message1", 1, LocalDateTime.parse("2025-01-01T12:00"), 1);
        testAnswers =  List.of(testAnswer);
        testAnswerDTO = new AnswerDTO(1,"title1", "message1", 1, LocalDateTime.parse("2025-01-01T12:00"), 1);
        testAnswerDTOs =  List.of(testAnswerDTO);
        newAnswerDTO = new NewAnswerDTO("title2", "message", 1, 1);

    }

    @Test
    public void getAllAnswers_whenAnswersFound_thenReturnRightList(){

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

    @Test
    public void getAnswerById_whenAnswerFound_thenReturnRightAnswerDTO(){
        AnswerDTO result = service.getAnswerById(1);
        assertEquals(testAnswerDTO, result);
    }

    @Test
    public void getAnswerById_whenAnswerNotFound_thenThrowsRuntimeException(){
        testAnswer = null;
        assertThrows(RuntimeException.class, ()->service.getAnswerById(1));
    }


    @Test
    public void deleteAnswerById_whenAnswerFound_thenThrowsNothing(){
        assertDoesNotThrow(()-> service.deleteAnswerById(1));
    }

    @Test
    public void deleteAnswerById_whenAnswerNotFound_thenThrowsRuntimeException(){
        isExceptionNeeded = true;
        assertThrows(RuntimeException.class, ()->service.deleteAnswerById(1));
    }

    @Test
    public void addNewAnswer_whenCreated_thenThrowsNothing(){
        assertDoesNotThrow(()->service.addNewAnswer(newAnswerDTO));
    }

    @Test
    public void addNewAnswer_whenDidNotCreated_thenThrowsRuntimeException(){
        isExceptionNeeded = true;
        assertThrows(RuntimeException.class, ()->service.addNewAnswer(newAnswerDTO));
    }


}
