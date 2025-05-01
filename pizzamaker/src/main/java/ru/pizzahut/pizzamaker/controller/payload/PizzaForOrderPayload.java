package ru.pizzahut.pizzamaker.controller.payload;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PizzaForOrderPayload(
        Integer id,
        String name,
        String size,
        Integer pizzaBoardId,
        @NotBlank
        Integer pizzaBaseId,
        Integer orderId,
        List<String> ingredients) {
}
