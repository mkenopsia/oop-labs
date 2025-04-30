package ru.pizzahut.pizzamaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pizzahut.pizzamaker.controller.payload.PizzaBasePayload;
import ru.pizzahut.pizzamaker.service.PizzaBaseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pizzamaker/pizzabase/{pizzabaseId:\\d+}")
public class PizzaBaseController {

    private final PizzaBaseService pizzaBaseService;

    @PatchMapping()
    public ResponseEntity<?> updatePizzabase(@PathVariable("pizzabaseId") Integer id,
                                              @RequestBody PizzaBasePayload payload) {
        this.pizzaBaseService.update(id, payload);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<?> deletePizzabase(@PathVariable("pizzabaseId") Integer id) {
        this.pizzaBaseService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<?> getPizzabase(@PathVariable("pizzabaseId") Integer id) {
        return ResponseEntity.ok().body(this.pizzaBaseService.findPizzaBaseById(id));
    }
}
