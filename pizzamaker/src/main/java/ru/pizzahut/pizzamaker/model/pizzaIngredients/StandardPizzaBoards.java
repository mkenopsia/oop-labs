package ru.pizzahut.pizzamaker.model.pizzaIngredients;

import lombok.Getter;

@Getter
public enum StandardPizzaBoards {
    CLASSIC("Классический бортик", 0.0), CHEESE("Сырный бортик", 50.0), GARLIC("Чесночный бортик", 40.0),
    BACON("Бортик с беконом", 54.0), SESAME("Кунжутный бортик", 15.0);

    private String type;
    private Double price;

    StandardPizzaBoards(String type, Double price) {
        this.type = type;
        this.price = price;
    }
}
