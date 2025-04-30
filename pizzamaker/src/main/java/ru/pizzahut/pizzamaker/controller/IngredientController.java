package ru.pizzahut.pizzamaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pizzahut.pizzamaker.controller.payload.IngredientPayload;
import ru.pizzahut.pizzamaker.service.IngredientService;

@RestController
@RequestMapping("/pizzamaker/ingredient/{ingredientId:\\d+}")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @PatchMapping()
    public ResponseEntity<?> updateIngredient(@PathVariable("ingredientId") Integer id,
                                              @RequestBody IngredientPayload payload) {
        this.ingredientService.update(id, payload);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteIngredient(@PathVariable("ingredientId") Integer id) {
        this.ingredientService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<?> getProduct(@PathVariable("ingredientId") Integer id) {
        return ResponseEntity.ok().body(this.ingredientService.findByIngredientId(id));
    }

}
