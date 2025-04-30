package ru.pizzahut.pizzamaker.controller.payload;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PizzaPayload(
        @NotBlank(message = "{pizza.error.blank_name}")
        String name,
        @NotNull(message = "{pizza.error.empty_ingredients}")
        @Size(min = 1, max = 12, message = "{pizza.error.ivalid_ingredients_num}")
        List<String> ingredients,
        @NotBlank(message = "{pizza.error.blank_pizzabase}")
        String base,
        String board,
        String size
) {
}
