package ru.pizzahut.pizzamaker.controller.payload;

public record PizzaBasePayload(
        String type,
        Double price) {
}
