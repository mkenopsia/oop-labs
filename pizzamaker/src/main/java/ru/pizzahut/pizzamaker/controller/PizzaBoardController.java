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
@RequestMapping("/pizzamaker/pizzaboard/{pizzaBoardId:\\d+}")
public class PizzaBoardController {

    private final PizzaBoardService pizzaBoardService;

    @GetMapping
    public ResponseEntity<?> getPizzaBoard(@PathVariable("pizzaBoardId") Integer id) {
        return ResponseEntity.ok().body(this.pizzaBoardService.getPizzaBoardById(id));
    }

    @PatchMapping
    public ResponseEntity<?> updatePizzaBoard(@PathVariable("pizzaBoardId") Integer id,
                                              @RequestBody PizzaBoardPayload payload) {
        this.pizzaBoardService.update(id, payload);
        return ResponseEntity.ok().body(Map.of("message", "Борт обновлён успешно"));
    }

    @DeleteMapping
    public ResponseEntity<?> deletePizzaBoard(@PathVariable("pizzaBoardId") Integer id) {
        this.pizzaBoardService.delete(id);
        return ResponseEntity.ok().body(Map.of("message", "Борт удалён успешно"));
    }
}
