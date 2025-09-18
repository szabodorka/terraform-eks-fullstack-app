package com.codecool.askmateoop.controller;

import com.codecool.askmateoop.controller.dto.question.NewQuestionDTO;
import com.codecool.askmateoop.controller.dto.question.QuestionDTO;
import com.codecool.askmateoop.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/question")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/all")
    public List<QuestionDTO> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public QuestionDTO getQuestionById(@PathVariable int id) {
        return questionService.getQuestionById(id);
    }

    @GetMapping("/search")
    public List<QuestionDTO> searchQuestions(@RequestParam String searchTerm) {
        return questionService.searchQuestionsBySearchTerm(searchTerm);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewQuestion(@RequestBody NewQuestionDTO question) {
        questionService.addNewQuestion(question);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestionById(@PathVariable int id) {
        questionService.deleteQuestionById(id);
    }
}
