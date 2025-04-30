package ru.pizzahut.pizzamaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pizzahut.pizzamaker.controller.payload.IngredientPayload;
import ru.pizzahut.pizzamaker.service.IngredientService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pizzamaker/ingredients")
public class IngredientsController {

    private final IngredientService ingredientService;

    @PostMapping()
    public ResponseEntity<?> addIngredient(@RequestBody IngredientPayload payload) {
        this.ingredientService.save(payload);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity<?> getIngredients() {
        return ResponseEntity.ok().body(this.ingredientService.getIngredients());
    }
}
