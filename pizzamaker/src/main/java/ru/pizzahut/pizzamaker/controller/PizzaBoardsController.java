package ru.pizzahut.pizzamaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pizzahut.pizzamaker.service.PizzaBoardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pizzamaker/boards")
public class PizzaBoardsController {

    private final PizzaBoardService pizzaBoardService;

    @GetMapping
    private ResponseEntity<?> getAllPizzaBoards() {
        return ResponseEntity.ok().body(this.pizzaBoardService.getPizzaBoards());
    }

}
