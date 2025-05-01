package ru.pizzahut.pizzamaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pizzahut.pizzamaker.controller.payload.PizzaBoardPayload;
import ru.pizzahut.pizzamaker.service.api.PizzaBoardService;
import ru.pizzahut.pizzamaker.service.impl.DefaultPizzaBoardService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pizzamaker/pizzaboards")
public class PizzaBoardsController {

    private final PizzaBoardService pizzaBoardService;

    @PostMapping
    private ResponseEntity<?> savePizzaBoard(@RequestBody PizzaBoardPayload payload) {
        this.pizzaBoardService.save(payload);
        return ResponseEntity.ok().body(Map.of("message", "Борт сохранён успешно"));
    }

    @GetMapping
    private ResponseEntity<?> getAllPizzaBoards() {
        return ResponseEntity.ok().body(this.pizzaBoardService.getAllPizzaBoards());
    }

}
