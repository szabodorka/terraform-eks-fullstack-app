package com.codecool.askmateoop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ExampleController {

//    Endpoint examples for Server Side Rendering
//    Can be used instead of a frontend framework (like React)
//    "index" is the name of the html file sent to the user, located in the src/main/resources/templates folder

    @GetMapping
    public String index(Model model) {
        model.addAttribute("name", "Example name");
        return "index";
    }

    @GetMapping("/path/{name}")
    public String exampleWithPathVariable(@PathVariable String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @GetMapping("/param")
    public String exampleWithRequestParam(@RequestParam String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }
}

