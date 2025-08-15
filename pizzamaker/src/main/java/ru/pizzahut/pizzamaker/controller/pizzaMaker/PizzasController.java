package ru.pizzahut.pizzamaker.controller.pizzaMaker;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pizzahut.pizzamaker.controller.payload.PizzaPayload;
import ru.pizzahut.pizzamaker.service.api.PizzaService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pizzamaker/pizzas")
public class PizzasController {

    private final PizzaService pizzaService;

    @PostMapping()
    public ResponseEntity<?> addPizza(@RequestBody @Valid PizzaPayload pizzaPayload,
                                      BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        this.pizzaService.save(pizzaPayload);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{filter:.+}")
    public ResponseEntity<?> getPizzasWithIngredientsMatchingFilter(@PathVariable("filter") String filter) {
        return ResponseEntity.ok().body(this.pizzaService.getFilteredPizzas(filter));
    }

    @GetMapping()
    public ResponseEntity<?> getAllPizzas() {
        return ResponseEntity.ok().body(this.pizzaService.getAllPizzas());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindingException(BindException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty("message", exception.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage));
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
