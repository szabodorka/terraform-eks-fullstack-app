package com.codecool.askmateoop.controller;

import com.codecool.askmateoop.controller.dto.answer.NewAnswerDTO;
import com.codecool.askmateoop.controller.dto.answer.AnswerDTO;
import com.codecool.askmateoop.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@RestController
@RequestMapping("api/answer")
public class AnswerController {
    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<AnswerDTO>> getAllAnswers() {
        try {
            List<AnswerDTO> answerDTOs = answerService.getAllAnswers();
            return ResponseEntity.ok(answerDTOs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerDTO> getAnswerById(@PathVariable int id) {
        try {
            AnswerDTO answerDTO = answerService.getAnswerById(id);
            return ResponseEntity.ok(answerDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // or INTERNAL_SERVER_ERROR if more appropriate
        }
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<AnswerDTO>> getAnswersForQuestion(@PathVariable int questionId) {
        try {
            List<AnswerDTO> answerDTOs = answerService.getAnswersByQuestionId(questionId);
            return ResponseEntity.ok(answerDTOs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> addNewAnswer(@RequestBody NewAnswerDTO newAnswerDTO) {
        try {
            answerService.addNewAnswer(newAnswerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswerById(@PathVariable int id) {
        try {
            answerService.deleteAnswerById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
