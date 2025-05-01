package ru.pizzahut.pizzamaker.controller.payload;

import java.util.List;

public record PizzaForOrderPayload(
        String name,
        String size,
        Integer pizzaBoardId,
        Integer orderId,
        List<String> ingredients) {
}
