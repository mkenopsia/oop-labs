package ru.pizzahut.pizzamaker.model.pizzaIngredients.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PizzaSize {

    public PizzaSize(String type, Double coef) {
        this.type = type;
        this.coef = coef;
    }

    private Integer id;
    private String type;
    private Double coef;
}
