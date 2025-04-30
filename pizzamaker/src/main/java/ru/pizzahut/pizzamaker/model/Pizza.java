package ru.pizzahut.pizzamaker.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.*;

import java.util.List;

@Data
@NoArgsConstructor
public class Pizza {

    private Integer id;
    private String name;
    private List<Ingredient> ingredients;
    private PizzaBase pizzaBase;
    private PizzaBoard pizzaBoard;
    private Double price;
    private PizzaSize pizzaSize;
}
