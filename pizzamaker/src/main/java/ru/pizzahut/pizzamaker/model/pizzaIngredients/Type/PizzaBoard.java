package ru.pizzahut.pizzamaker.model.pizzaIngredients.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PizzaBoard {

    public PizzaBoard(String type, Double price) {
        this.type = type;
        this.price = price;
    }

    private Integer id;
    private String type;
    private Double price;
}
