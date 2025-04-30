package ru.pizzahut.pizzamaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pizzahut.pizzamaker.controller.payload.PizzaBasePayload;
import ru.pizzahut.pizzamaker.service.PizzaBaseService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pizzamaker/pizzabases")
public class PizzaBasesController {

    private final PizzaBaseService pizzaBaseService;

    @PostMapping()
    public ResponseEntity<?> addPizzabase(@RequestBody PizzaBasePayload pizzaBasePayload) {
        pizzaBaseService.save(pizzaBasePayload);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<?> getPizzabases() {
        return ResponseEntity.ok().body(this.pizzaBaseService.getAllPizzaBases());
    }
}
