package ru.pizzahut.pizzamaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pizzahut.pizzamaker.controller.payload.PizzaBasePayload;
import ru.pizzahut.pizzamaker.service.api.PizzaBaseService;
import ru.pizzahut.pizzamaker.service.impl.DefaultPizzaBaseService;

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

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<?> handleInvalidPizzaBoardPriceCase(IllegalArgumentException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty("message", exception.getMessage());
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
