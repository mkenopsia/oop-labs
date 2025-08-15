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

import java.util.Map;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/pizzamaker/pizza/{pizzaId:\\d+}")
public class PizzaController {

    private final PizzaService pizzaService;

    @PatchMapping()
    public ResponseEntity<?> updatePizza(@PathVariable("pizzaId") Integer id,
                                         @RequestBody @Valid PizzaPayload pizzaPayload,
                                         BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        this.pizzaService.updatePizza(id, pizzaPayload);
        return ResponseEntity.ok().body(Map.of("message", "Пицца обновлена"));
    }

    @GetMapping()
    public ResponseEntity<?> getPizza(@PathVariable("pizzaId") Integer id) {
        return ResponseEntity.ok().body(this.pizzaService.getPizzaById(id));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindingException(BindException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty("message", exception.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage));
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
