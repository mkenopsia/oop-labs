package ru.pizzahut.pizzamaker.controller.payload;

import ru.pizzahut.pizzamaker.model.pizzaForOrder.PizzaForOrder;

import java.time.LocalDateTime;
import java.util.List;

public record OrderPayload(
        Integer userId,
        LocalDateTime date,
        String status,
        String comment,
        Double price,
        List<PizzaForOrderPayload> pizzasForOrder) {

}
