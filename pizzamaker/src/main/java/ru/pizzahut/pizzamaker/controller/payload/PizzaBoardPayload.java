package ru.pizzahut.pizzamaker.controller.payload;

import jakarta.validation.constraints.Size;

import java.util.List;

public record PizzaBoardPayload(
        String type,
        @Size(min = 1, max = 12, message = "{pizza.error.ivalid_ingredients_num}")
        List<String> ingredients,
        List<String> availablePizzas,
        Double price
) {
}
