package com.codecool.askmateoop.controller;

import com.codecool.askmateoop.controller.dto.answer.NewAnswerDTO;
import com.codecool.askmateoop.controller.dto.answer.AnswerDTO;
import com.codecool.askmateoop.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<AnswerDTO> getAllAnswers() {
        try{
            return answerService.getAllAnswers();
        } catch (RuntimeException e) {
            //send back appropriate response code with empty body
            return null;
        }
    }

    @GetMapping("/{id}")
    public AnswerDTO getAnswerById(@PathVariable int id) {
        try{
            return answerService.getAnswerById(id);
        } catch (RuntimeException e) {
            //send back appropriate response code with empty body
            return null;
        }
    }

    @PostMapping("/")
    public void addNewAnswer(@RequestBody NewAnswerDTO newAnswerDTO) {
        try{
            answerService.addNewAnswer(newAnswerDTO);
        } catch (RuntimeException e) {
            //send back appropriate response code with empty body
        }
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}")
    public void deleteAnswerById(@PathVariable int id) {
        try{
            answerService.deleteAnswerById(id);
        } catch (RuntimeException e) {
            //send back appropriate response code with empty body
        }
    }
}
